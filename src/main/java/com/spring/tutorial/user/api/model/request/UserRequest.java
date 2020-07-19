package com.spring.tutorial.user.api.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.tutorial.user.persistence.enumeration.Gender;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {
    private String name;
    private Gender gender;
    private String country;

}
