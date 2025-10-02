package com.kutit.demo.data.models;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.hibernate.validator.constraints.URL;
import java.time.Instant;

@Data
@Document(collection = "urls")
public class Link {

    @Id
    private String id;

    @URL(message = "URL is not valid")
    @NotBlank(message = "URL cannot be blank")
    private String original;


    private String shortened;


    private int clicks;


    @CreatedDate
    private Instant createdOn;


    @LastModifiedDate
    private Instant updatedOn;

    @DBRef
    private User user;

}
