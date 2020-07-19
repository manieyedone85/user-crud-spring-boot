package com.spring.tutorial.user.service;

import com.spring.tutorial.user.api.model.request.UserRequest;
import com.spring.tutorial.user.api.model.response.UserResponse;
import com.spring.tutorial.user.common.Helper;
import com.spring.tutorial.user.exception.ApiException;
import com.spring.tutorial.user.persistence.enumeration.Status;
import com.spring.tutorial.user.persistence.repository.UserRepository;
import com.spring.tutorial.user.persistence.schema.User;
import com.spring.tutorial.user.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.spring.tutorial.user.common.Constants.NO_CONTENT;
import static com.spring.tutorial.user.common.Constants.NO_DATA_FOUND;

@Service
@Slf4j
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;

    public Flux<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .switchIfEmpty(Flux.error(new ApiException(NO_DATA_FOUND, HttpStatus.NO_CONTENT, NO_CONTENT, NO_DATA_FOUND)))
                .map(user -> UserMapper.toUserResponse(user));
    }

    public Mono<UserResponse> createUser(UserRequest userRequest) {

        return userRepository.findOneByName(userRequest.getName())
                .flatMap(user -> save(user, userRequest))
                .switchIfEmpty(save(null, userRequest))
                .map(user1 -> UserMapper.toUserResponse(user1));
    }

    public Mono<UserResponse> updateUser(String userId,
                                         UserRequest userRequest) {
        return userRepository.findById(userId)
                .flatMap(existingUser -> save(existingUser, userRequest))
                .map(updateUser -> UserMapper.toUserResponse(updateUser))
                .switchIfEmpty(Mono.error(new ApiException(NO_DATA_FOUND, HttpStatus.NO_CONTENT, NO_CONTENT, NO_DATA_FOUND)));
    }

    public Mono<?> deleteUser(String userId, User user) {

        return userRepository.findById(userId)
                .flatMap(existingUser -> {
                    existingUser.setStatus(Status.DELETED);
                    log.info("Delete user Response for userId={}", userId);
                    return userRepository.save(existingUser);
                })
                .map(updateStore -> new ResponseEntity<>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private Mono<User> save(User user,
                            UserRequest userRequest) {
        if (user == null)
            user = new User();

        user.setName(userRequest.getName());
        user.setGender(userRequest.getGender());
        user.setCountry(userRequest.getCountry());
        user.setStatus((user.getStatus() == null) ? Status.ACTIVE : user.getStatus());
        user.setCreatedDate(Helper.getLocalUtcDateTime());
        user.setLastModifiedDate(Helper.getLocalUtcDateTime());
        return userRepository.save(user);
    }
}
