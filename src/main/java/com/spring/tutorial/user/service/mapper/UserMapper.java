package com.spring.tutorial.user.service.mapper;


import com.spring.tutorial.user.api.model.request.UserRequest;
import com.spring.tutorial.user.api.model.response.UserResponse;
import com.spring.tutorial.user.common.Helper;
import com.spring.tutorial.user.persistence.schema.User;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .gender(user.getGender())
                .country(user.getCountry())
                .status(user.getStatus())
                .build();
    }

    public static User toUser(UserRequest userRequest) {
        return User.builder()
                .country(userRequest.getCountry())
                .name(userRequest.getName())
                .gender(userRequest.getGender())
                .createdDate(Helper.getLocalUtcDateTime())
                .lastModifiedDate(Helper.getLocalUtcDateTime())
                .build();
    }
}
