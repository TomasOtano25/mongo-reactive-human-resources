package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByMobile(String mobile);
}
