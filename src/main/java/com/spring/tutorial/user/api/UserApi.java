package com.spring.tutorial.user.api;

import com.spring.tutorial.user.api.model.request.UserRequest;
import com.spring.tutorial.user.api.model.response.UserResponse;
import com.spring.tutorial.user.common.Constants;
import com.spring.tutorial.user.common.WithUser;
import com.spring.tutorial.user.persistence.schema.User;
import com.spring.tutorial.user.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by mani on 19/07/2020.
 */
@RestController
@RequestMapping(path = {Constants.API_USER_CONTEXT_PATH}, produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class UserApi {
    @Autowired
    UserServiceImpl userService;

    /**
     * Method to get all created users from the db.
     *
     * @return
     */
    @GetMapping()
    public Flux<UserResponse> getAllUsers() {
        log.info("Get All users");
        return userService.getAllUsers();
    }

    /**
     * Method to create create User to the db.
     *
     * @return
     */
    @PostMapping()
    public Mono<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        log.info("Post user Request data={} ", userRequest.toString());
        return userService.createUser(userRequest);
    }

    /**
     * Method to patch User to the db.
     *
     * @return
     */
    @PatchMapping("/{id}")
    public Mono<UserResponse> updateUser(@PathVariable(value = "id") String userId,
                                         @RequestBody UserRequest userRequest) {
        log.info("Patch user Request userId={} ", userId);
        return userService.updateUser(userId, userRequest);
    }

    @DeleteMapping("/{id}")
    public Mono<?> deleteUser(@PathVariable(value = "id") String userId,
                                                   @WithUser User user) {
        log.info("Delete user Request userId={} ", userId);
        return userService.deleteUser(userId, user);
    }
}
