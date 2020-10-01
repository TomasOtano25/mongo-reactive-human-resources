package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.User;
import com.one.mongoreactivehumanresources.dtos.UserMinimumDto;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserReactRepository extends ReactiveSortingRepository<User, String> {
    Mono<User> findByMobile(String mobile);

    @Query(value = "{}", fields = "{ '_id' : 0, 'mobile': 1, 'username' : 1, 'roles': []}")
    Flux<UserMinimumDto> findAllUsers();

    Flux<User> findByMobileOrUsernameOrEmail(String mobile, String username, String email);
}
