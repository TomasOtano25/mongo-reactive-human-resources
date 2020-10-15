package com.one.mongoreactivehumanresources.bussiness_controllers;

import com.one.mongoreactivehumanresources.bussiness_services.JwtService;
import com.one.mongoreactivehumanresources.documents.Role;
import com.one.mongoreactivehumanresources.documents.User;
import com.one.mongoreactivehumanresources.dtos.TokenOutputDto;
import com.one.mongoreactivehumanresources.dtos.UserCredentialDto;
import com.one.mongoreactivehumanresources.dtos.UserDto;
import com.one.mongoreactivehumanresources.dtos.UserMinimumDto;
import com.one.mongoreactivehumanresources.exceptions.ConflictException;
import com.one.mongoreactivehumanresources.exceptions.ForbiddenException;
import com.one.mongoreactivehumanresources.exceptions.NotFoundException;
import com.one.mongoreactivehumanresources.repositories.UserReactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private UserReactRepository userReactRepository;
    private JwtService jwtService;

    @Autowired
    public UserController(UserReactRepository userReactRepository, JwtService jwtService) {
        this.userReactRepository = userReactRepository;
        this.jwtService = jwtService;
    }

    public Mono<TokenOutputDto> login(String mobile) {
        return this.userReactRepository.findByMobile(mobile).map(
                user -> {
                    String[] roles = Arrays.stream(user.getRoles()).map(Role::name).toArray(String[]::new);
                    return new TokenOutputDto(jwtService.createToken(user.getMobile(),user.getUsername(), roles));
                }
        );
    }

    private Mono<Void> notExistByMobile(String mobile) {
        return this.userReactRepository.findByMobile(mobile)
                .handle((document, sink) -> sink.error(new ConflictException("The mobile already exists")));
    }

    public Mono<UserDto> createUser(UserDto userDto) {
        Mono<Void> notExistByMobile = this.notExistByMobile(userDto.getMobile());
        User user = User.builder().mobile(userDto.getMobile()).username(userDto.getUsername()).email(userDto.getEmail()).build();
        return Mono.when(notExistByMobile).then(this.userReactRepository.save(user)).map(UserDto::new);
    }

    public Mono<UserDto> readUser(String mobile, String claimMobile, List<String> claimRoles) {
        return this.readAndValidate(mobile, claimMobile, claimRoles)
                .map(UserDto::new);
    }

    private Mono<User> readAndValidate(String mobile, String claimMobile, List<String> claimRoles) {
        return this.userReactRepository.findByMobile(mobile)
                .switchIfEmpty(Mono.error(new NotFoundException("User mobile:" + mobile)))
                .handle((user, sink) -> {
                    if (!this.isAuthorized(claimMobile, claimRoles, mobile, Arrays.stream(user.getRoles())
                            .map(Role::roleName).collect(Collectors.toList()))) {
                        sink.error(new ForbiddenException("User mobile (" + mobile + ")"));
                    } else {
                        sink.next(user);
                    }
                });
    }

    private boolean isAuthorized(String claimMobile, List<String> claimRoles, String userMobile, List<String> userRoles) {
        if(claimRoles.contains(Role.ADMIN.roleName()) || claimMobile.equals(userMobile)) {
            return true;
        }
        return userRoles.stream().allMatch(role -> role.equals(Role.CUSTOMER.roleName()));
    }

    public Flux<UserMinimumDto> readAll() {
        return this.userReactRepository.findAllUsers();
    }

    public Mono<UserDto> updateUser(String mobile, UserDto userDto) {
        Mono<User> user = this.userReactRepository.findByMobile(mobile)
                .switchIfEmpty(Mono.error(new NotFoundException("User mobile:" + mobile)))
                .map(user1 -> {
                    user1.setMobile(userDto.getMobile());
                    user1.setUsername(userDto.getUsername());
                    user1.setEmail(userDto.getEmail());
                    user1.setActive(userDto.isActive());
                    return user1;
                });
        Mono<Void> noExistByMobile;
        if(mobile.equals(userDto.getMobile())) {
            noExistByMobile = Mono.empty();
        } else {
            noExistByMobile = this.notExistByMobile(userDto.getMobile());
        }
        return Mono.when(user, noExistByMobile).then(this.userReactRepository.saveAll(user).next()).map(UserDto::new);
    }

    public Mono<UserDto> changePassword(String mobile, UserCredentialDto userCredentialDto) {
        Mono<User> user = this.userReactRepository.findByMobile(mobile)
                .switchIfEmpty(Mono.error(new NotFoundException("User mobile:" + mobile)))
                .map(user1 -> {
                    user1.setPassword(userCredentialDto.getNewPassword());
                    return user1;
                });
        return Mono.when(user).then(this.userReactRepository.saveAll(user).next()).map(UserDto::new);
    }

    public Mono<UserDto> updateRoles(String mobile, UserMinimumDto userMinimumDto) {
        Mono<User> user = this.userReactRepository.findByMobile(mobile)
                .switchIfEmpty(Mono.error(new NotFoundException("User mobile:" + mobile)))
                .map(user1 -> {
                    user1.setRoles(userMinimumDto.getRoles());
                    return user1;
                });
        return Mono.when(user).then(this.userReactRepository.saveAll(user).next()).map(UserDto::new);
    }
}
