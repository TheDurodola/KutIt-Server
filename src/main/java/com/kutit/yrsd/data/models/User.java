package com.kutit.yrsd.data.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "users")
public class User {


    @Id
    private String id;

    @NotBlank(message = "Firstname cannot be blank")
    @Size(min = 2, max = 40, message = "Firstname must be between 2 and 40 characters")
    private String firstname;

    @NotBlank(message = "Lastname cannot be blank")
    @Size(min = 2, max = 40, message = "Lastname must be between 2 and 40 characters")
    private String lastname;

    @NotBlank(message = "Username cannot be blank")
    @Indexed(unique = true)
    @Min(value = 3, message = "Username must be at least 3 characters")
    private String username;

    @Indexed(unique = true)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @CreatedDate
    private Instant createdOn;

    @LastModifiedDate
    private Instant updatedOn;
}
