package com.kutit.yrsd.services;

import com.kutit.yrsd.data.models.User;
import com.kutit.yrsd.data.repositories.Entries;
import com.kutit.yrsd.data.repositories.Users;
import com.kutit.yrsd.dtos.requests.LoginUserRequest;
import com.kutit.yrsd.dtos.requests.RegisterUserRequest;
import com.kutit.yrsd.dtos.responses.RegisterUserResponse;
import com.kutit.yrsd.exceptions.*;
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

    @Autowired
    Entries entries;

    @BeforeEach
    void setUp() {
        entries.deleteAll();
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
        User user = users.findByUsername("lord_boj").get();
        assertNotNull(response);
//        assertEquals("Password123", user.getPassword());
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
        register.setEmail("bolaJidudola@Gmail.com ");
        register.setPassword("   Password123$   ");
        register.setFirstName("ayomide  ");
        register.setLastName("adeniyi-oso");
        register.setUsername("Lo_Boj ");
        response = authServices.register(register);
        assertEquals("Ayomide Adeniyi-Oso", response.getFullName());
    }

    @Test
    void testThatInvalidEmailThrowsException(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodolaGmail.com ");
        register.setPassword("   Password123$   ");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Boj ");
        assertThrows(InvalidEmailFormatException.class, ()-> authServices.register(register));
    }

    @Test
    void testThatInvalidPasswordThrowsException(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("      ");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Boj ");
        assertThrows(InvalidPasswordFormatException.class, ()-> authServices.register(register));
        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("12345");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Boj ");
        assertThrows(InvalidPasswordFormatException.class, ()-> authServices.register(register));

        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("password");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Boj ");
        assertThrows(InvalidPasswordFormatException.class, ()-> authServices.register(register));

        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("    23  ");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Boj ");
        assertThrows(InvalidPasswordFormatException.class, ()-> authServices.register(register));

    }

    @Test
    void testThatUsernameAlreadyExistsThrowsException() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("Password123$");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("Lo");
        assertThrows(InvalidUsernameFormatException.class, () -> authServices.register(register));

        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("Password123$");
        register.setFirstName("aBOLaji");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Bo#j ");
        assertThrows(InvalidUsernameFormatException.class, () -> authServices.register(register));

    }

    @Test
    void testThatInvalidNameThrowsException() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("Password123$");
        register.setFirstName("a");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Boj ");
        assertThrows(InvalidNameFormatException.class, ()-> authServices.register(register));
        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("Password123$");
        register.setLastName("dUROdolA");
        register.setUsername("LorD_Boj ");
        assertThrows(InvalidNameFormatException.class, ()-> authServices.register(register));

        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("Password123$");
        register.setLastName("dU");
        register.setFirstName("aBOLaji");
        register.setUsername("LorD_Boj ");
        assertThrows(InvalidNameFormatException.class, ()-> authServices.register(register));

        register.setEmail("bolajidurodola@Gmail.com ");
        register.setPassword("Password123$");
        register.setFirstName("aBOLaji");
        register.setLastName("#########");
        register.setUsername("LorD_Boj ");
        assertThrows(InvalidNameFormatException.class, ()-> authServices.register(register));
    }

    @Test
    void testThatDuplicateUsernameThrowsException(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        register = new RegisterUserRequest();
        register.setEmail("durodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserRequest finalRegister = register;
        assertThrows( UsernameAlreadyExistsException.class , ()-> authServices.register(finalRegister));

    }


    @Test
    void testThatDuplicateEmailThrowsException(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj_");
        assertThrows( EmailAlreadyExistsException.class , ()-> authServices.register(register));
    }


    @Test
    void testThatPasswordIsntSavedAsPlainText() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        User user = users.findByUsername("lord_boj").get();
        assertNotEquals("Password1!", user.getPassword());
    }



    @Test
    void testThatLoginReturnsWorks(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        LoginUserRequest login = new LoginUserRequest();
        login.setIdentifier("lord_boj");
        login.setPassword("Password1!");
        assertNotNull(authServices.login(login));



        login.setIdentifier("bolajidurodola@gmail.com");
        login.setPassword("Password1!");
        assertNotNull(authServices.login(login));

    }

    @Test
    void testThatInvalidLoginThrowsException(){
        LoginUserRequest login = new LoginUserRequest();
        login.setIdentifier("lord_boj");
        login.setPassword("Password1!");
        assertThrows(UserDoesntExistException.class, ()->authServices.login(login));
    }

}