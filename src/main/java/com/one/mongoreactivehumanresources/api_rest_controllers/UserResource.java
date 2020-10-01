package com.one.mongoreactivehumanresources.api_rest_controllers;

import com.one.mongoreactivehumanresources.bussiness_controllers.UserController;
import com.one.mongoreactivehumanresources.dtos.TokenOutputDto;
import com.one.mongoreactivehumanresources.dtos.UserCredentialDto;
import com.one.mongoreactivehumanresources.dtos.UserDto;
import com.one.mongoreactivehumanresources.dtos.UserMinimumDto;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String MOBILE_ID = "/{mobile}";

    private UserController userController;

    @Autowired
    public UserResource(UserController userController) {
        this.userController = userController;
    }

    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public Mono<TokenOutputDto> login(@AuthenticationPrincipal User activeUser) {
        return userController.login(activeUser.getUsername())
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PostMapping
    public Mono<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return this.userController.createUser(userDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PutMapping(value = MOBILE_ID)
    public Mono<UserDto> updateUser(@PathVariable String mobile, @Valid @RequestBody UserDto userDto) {
        return this.userController.updateUser(mobile, userDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @GetMapping(value = MOBILE_ID)
    public Mono<UserDto> read(@PathVariable String mobile) {
        return this.userController.readUser(mobile, SecurityContextHolder.getContext().getAuthentication().getName(),
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PatchMapping(value = "/password" + MOBILE_ID)
    public Mono<UserDto> changePassword(@PathVariable String mobile, @Valid @RequestBody UserCredentialDto userCredentialDto) {
        return this.userController.changePassword(mobile, userCredentialDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PatchMapping(value = MOBILE_ID)
    public Mono<UserDto> updateRoles(@PathVariable String mobile, @Valid @RequestBody UserMinimumDto userMinimumDto) {
        return this.userController.updateRoles(mobile, userMinimumDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

}
