package com.spring.tutorial.user.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.tutorial.user.persistence.enumeration.Gender;
import com.spring.tutorial.user.persistence.enumeration.Status;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private String id;
    private String name;
    private Gender gender;
    private String country;
    private Status status;

}
