package com.spring.tutorial.user.persistence.repository;

import com.spring.tutorial.user.persistence.schema.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findOneByName(String name);
}
