package com.spring.tutorial.user.persistence.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.tutorial.user.persistence.enumeration.Gender;
import com.spring.tutorial.user.persistence.enumeration.Status;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private Gender gender;
    private String country;
    private String deletedBy;
    @Builder.Default
    private Status status = Status.ACTIVE;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @CreatedDate
    private LocalDateTime createdDate;
    @Version
    private String version;
}
