package com.kutit.yrsd.services;

import com.kutit.yrsd.data.models.User;
import com.kutit.yrsd.data.repositories.Users;
import com.kutit.yrsd.dtos.requests.RegisterUserRequest;
import com.kutit.yrsd.dtos.responses.RegisterUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServicesImplTest {

    @Autowired
    AuthServicesImpl authServices;

    @Autowired
    Users users;

    @BeforeEach
    void setUp() {
        users.deleteAll();
    }

    @Test
    void testRegisterUser() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        assertNotNull(response);
    }

    @Test
    void testThatEmailFieldIsProperlyFormattedBeforeStoring(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolaJidurOdola@Gmail.com ");
        register.setPassword("Password123$");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        assertNotNull(response);
        assertEquals("bolajidurodola@gmail.com", response.getEmail());
    }

    @Test
    void testThatPasswordFieldIsProperlyFormattedBeforeStoring(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolaJidurOdola@Gmail.com ");
        register.setPassword("   Password123   ");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        User user = users.findByUsername("lord_boj");
        assertNotNull(response);
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testThatUsernameFieldIsProperlyFormattedBeforeStoring(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolaJidurOdola@Gmail.com ");
        register.setPassword("   Password123$   ");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Boj ");
        RegisterUserResponse response = authServices.register(register);
        assertNotNull(response);
        assertEquals("lord_boj", response.getUsername());
    }

    @Test
    void testThatNameFieldIsProperlyFormattedBeforeStoring(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolaJidurOdola@Gmail.com ");
        register.setPassword("   Password123$   ");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Boj ");
        RegisterUserResponse response = authServices.register(register);
        assertEquals("Abolaji Durodola", response.getFullName());



        register = new RegisterUserRequest();
        register.setEmail("bolaJidurOdola@Gmail.com ");
        register.setPassword("   Password123$   ");
        register.setFirstName("ayomide  ");
        register.setLastName("adeniyi-oso");
        register.setUsername("LorD_Boj ");
        response = authServices.register(register);
        assertEquals("Ayomide Adeniyi-Oso", response.getFullName());
    }

}