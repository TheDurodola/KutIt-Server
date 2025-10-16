package com.kutit.yrsd.services;

import com.kutit.yrsd.data.repositories.Entries;
import com.kutit.yrsd.data.repositories.Users;
import com.kutit.yrsd.dtos.requests.*;
import com.kutit.yrsd.dtos.responses.CreateUserEntryResponse;
import com.kutit.yrsd.dtos.responses.GetUserEntryResponse;
import com.kutit.yrsd.dtos.responses.RegisterUserResponse;
import com.kutit.yrsd.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServicesImplTest {

    @Autowired
    private UserServices userServices;

    @Autowired
    private Entries entries;

    @Autowired
    private Users users;

    @Autowired
    private AuthServices authServices;

    @BeforeEach
    void setUp() {
        entries.deleteAll();
        users.deleteAll();
    }




    @Test
    void createUserEntry_EntriesCountIsOne() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());

        userServices.createUserEntry(request);
        assertEquals(1, entries.count());
    }


    @Test
    void createUserEntry_EntryBelongsToUser() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId("1");
        assertThrows(UnauthorizedUserException.class, ()-> userServices.createUserEntry(request));

    }


    @Test
    void abnormalUrlIsRejected() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://dev");
        request.setUserId("1");
        assertThrows(InvalidLinkFormatException.class, ()-> userServices.createUserEntry(request));

    }


    @Test
    void createUserEntry_GetEntryBack() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        CreateUserEntryResponse entryResponse = userServices.createUserEntry(request);

        GetUserEntryRequest getUserEntryRequest = new GetUserEntryRequest();
        getUserEntryRequest.setEntryId(entryResponse.getId());
        GetUserEntryResponse entry = userServices.getUserEntry(getUserEntryRequest);
        assertNotNull(entry);
        assertEquals(request.getOriginalLink(), entry.getOriginalLink());
        assertEquals(entryResponse.getShortenedLink(), entry.getShortenedLink());
    }



    @Test
    void createUserEntry_AttemptToGetNonExistentEntryWillThrowException() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        CreateUserEntryResponse entryResponse = userServices.createUserEntry(request);

        GetUserEntryRequest getUserEntryRequest = new GetUserEntryRequest();
        getUserEntryRequest.setEntryId("999");

        assertThrows(EntryDoesntExistException.class, () -> userServices.getUserEntry(getUserEntryRequest));

    }

    @Test
    void getAllEntriesCreatedByUser() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        userServices.createUserEntry(request);

        request.setOriginalLink("https://semicolonlan.com/login?dst=http%3A%2F%2Fwww.msftconnecttest.com%2Fredirect");
        request.setUserId(response.getId());

        userServices.createUserEntry(request);


        request.setOriginalLink("https://nodejs.org/docs/latest/api/");
        request.setUserId(response.getId());
        userServices.createUserEntry(request);

        GetAllUserEntriesRequest request1 = new GetAllUserEntriesRequest();
        request1.setUserId(response.getId());
        assertEquals(3, userServices.getAllUserEntries(request1).getEntries().size() );




    }

    @Test
    void getAllEntriesThrowsAnExcpetionIfUserIsYetToAddAnyEntry() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        GetAllUserEntriesRequest request1 = new GetAllUserEntriesRequest();
        request1.setUserId(response.getId());

        assertThrows(EntriesDontExistsException.class, () -> userServices.getAllUserEntries(request1));

    }

    @Test
    void getAllEntriesThrowsAnExceptionIfUserIsYetToAddAnyEntry() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        GetAllUserEntriesRequest request1 = new GetAllUserEntriesRequest();
        request1.setUserId("test");

        assertThrows(UserDoesntExistException.class, () -> userServices.getAllUserEntries(request1));

    }

    @Test
    void testThatEntryCreatedByUserCanBeDeletedByUser(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        CreateUserEntryResponse createUserEntryResponse = userServices.createUserEntry(request);

        request.setOriginalLink("https://semicolonlan.com/login?dst=http%3A%2F%2Fwww.msftconnecttest.com%2Fredirect");
        request.setUserId(response.getId());
        userServices.createUserEntry(request);
        DeleteUserEntryRequest delete = new DeleteUserEntryRequest();
        delete.setUserId(response.getId());
        delete.setEntryId(createUserEntryResponse.getId());
        userServices.deleteUserEntry(delete);
        assertEquals(1, entries.count());
    }


    @Test
    void attemptToDeleteEntryWithUnregisteredUserThrowsUserDoesntExistException(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        CreateUserEntryResponse createUserEntryResponse = userServices.createUserEntry(request);

        request.setOriginalLink("https://semicolonlan.com/login?dst=http%3A%2F%2Fwww.msftconnecttest.com%2Fredirect");
        request.setUserId(response.getId());
        userServices.createUserEntry(request);
        DeleteUserEntryRequest delete = new DeleteUserEntryRequest();
        delete.setUserId("666");
        delete.setEntryId(createUserEntryResponse.getId());
        assertThrows(UserDoesntExistException.class , () -> userServices.deleteUserEntry(delete));

    }



    @Test
    void attemptToDeleteEntryWithUnauthorizedUserThrowsUnauthorizedUserException(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        CreateUserEntryResponse createUserEntryResponse = userServices.createUserEntry(request);

        register.setEmail("boluwatife@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Boluwatife");
        register.setLastName("Durodola");
        register.setUsername("lord_bolu");
        response = authServices.register(register);

        request.setOriginalLink("https://semicolonlan.com/login?dst=http%3A%2F%2Fwww.msftconnecttest.com%2Fredirect");
        request.setUserId(response.getId());
        userServices.createUserEntry(request);
        DeleteUserEntryRequest delete = new DeleteUserEntryRequest();
        delete.setUserId(response.getId());
        delete.setEntryId(createUserEntryResponse.getId());
        assertThrows(UnauthorizedUserException.class , () -> userServices.deleteUserEntry(delete));

    }


    @Test
    void attemptToDeleteANonExistentEntryWouldThrowAnEntryDoesntExistException(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        CreateUserEntryResponse createUserEntryResponse = userServices.createUserEntry(request);

        DeleteUserEntryRequest delete = new DeleteUserEntryRequest();
        delete.setUserId(response.getId());
        delete.setEntryId("test");
        assertThrows(EntryDoesntExistException.class , () -> userServices.deleteUserEntry(delete));
    }



    @Test
    void updateEntryCreatedByUser_EntryIsUpdated() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        CreateUserEntryResponse createUserEntryResponse = userServices.createUserEntry(request);

        UpdateUserEntryRequest updateUserEntryRequest = new UpdateUserEntryRequest();
        updateUserEntryRequest.setUserId(response.getId());
        updateUserEntryRequest.setEntryId(createUserEntryResponse.getId());
        updateUserEntryRequest.setOriginalLink("https://nodejs.org/docs/latest/api/");
        userServices.updateUserEntry(updateUserEntryRequest);

        GetUserEntryRequest getUserEntryRequest = new GetUserEntryRequest();
        getUserEntryRequest.setEntryId(createUserEntryResponse.getId());
        assertEquals("https://nodejs.org/docs/latest/api/", userServices.getUserEntry(getUserEntryRequest)
                .getOriginalLink());
    }


    @Test
    void attemptToUpdateEntryWithUnregisteredUserThrowsUserDoesntExistException(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        CreateUserEntryResponse createUserEntryResponse = userServices.createUserEntry(request);

        UpdateUserEntryRequest updateUserEntryRequest = new UpdateUserEntryRequest();
        updateUserEntryRequest.setUserId("test");
        updateUserEntryRequest.setEntryId(createUserEntryResponse.getId());
        updateUserEntryRequest.setOriginalLink("https://nodejs.org/docs/latest/api/");
        assertThrows(UserDoesntExistException.class , () -> userServices.updateUserEntry(updateUserEntryRequest));
    }


    @Test
    void attemptToUpdateEntryWithWrongEntryIdThrowsEntryException(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        CreateUserEntryRequest request = new CreateUserEntryRequest();
        request.setOriginalLink("https://developer.mozilla.org/en-US/docs/Web/JavaScript");
        request.setUserId(response.getId());
        CreateUserEntryResponse createUserEntryResponse = userServices.createUserEntry(request);

        UpdateUserEntryRequest updateUserEntryRequest = new UpdateUserEntryRequest();
        updateUserEntryRequest.setUserId(response.getId());
        updateUserEntryRequest.setEntryId("test");
        updateUserEntryRequest.setOriginalLink("https://nodejs.org/docs/latest/api/");
        assertThrows(EntryDoesntExistException.class , () -> userServices.updateUserEntry(updateUserEntryRequest));
    }



    @Test
    void createAccount_ChangeAccountDetails(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        UpdateUserAccountRequest updateUserAccountRequest = new UpdateUserAccountRequest();
        updateUserAccountRequest.setId(response.getId());
        updateUserAccountRequest.setFirstName("Bolaji");
        updateUserAccountRequest.setLastName("Duro");
        updateUserAccountRequest.setUsername("lord_bolaji");
        updateUserAccountRequest.setEmail("yrsd@gmail.com");
        updateUserAccountRequest.setOldPassword("Password1!");
        updateUserAccountRequest.setNewPassword("DollarMustBeMade!1");
        userServices.updateUserAccount(updateUserAccountRequest);


        assertEquals("Bolaji", users.findById(response.getId()).get().getFirstname());
    }

    @Test
    void attemptToUpdateUserAccountDetails(){
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        UpdateUserAccountRequest updateUserAccountRequest = new UpdateUserAccountRequest();
        updateUserAccountRequest.setId(response.getId());
        updateUserAccountRequest.setFirstName("Bolaji");
        updateUserAccountRequest.setLastName("Duro");
        updateUserAccountRequest.setUsername("lord_bolaji");
        updateUserAccountRequest.setEmail("yrsd@gmail.com");
        updateUserAccountRequest.setOldPassword("IncorrectOldPassword");
        updateUserAccountRequest.setNewPassword("DollarMustBeMade!1");
        assertThrows(IncorrectUserPasswordException.class, ()->
                userServices.updateUserAccount(updateUserAccountRequest));
    }
    @Test
    void attemptToUpdateUserAccountDetailsWithUnregisteredUserThrowsUserDoesntExistException() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        UpdateUserAccountRequest updateUserAccountRequest = new UpdateUserAccountRequest();
        updateUserAccountRequest.setId("Test");
        updateUserAccountRequest.setFirstName("Bolaji");
        updateUserAccountRequest.setLastName("Duro");
        updateUserAccountRequest.setUsername("lord_bolaji");
        updateUserAccountRequest.setEmail("yrsd@gmail.com");
        updateUserAccountRequest.setOldPassword("IncorrectOldPassword");
        updateUserAccountRequest.setNewPassword("DollarMustBeMade!1");
        assertThrows(UserDoesntExistException.class, () ->
                userServices.updateUserAccount(updateUserAccountRequest));
    }

    @Test
    void getUserAccount_UserDetailsAreReturned() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        GetUserAccountRequest getUserAccountRequest = new GetUserAccountRequest();
        getUserAccountRequest.setUserId(response.getId());
        assertEquals("Abolaji", userServices.getUserAccount(getUserAccountRequest).getFirstName());

    }

    @Test
    void getUserAccount_UserDetailsThrowsExceptionIfUserIdIsIncorrect() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);

        GetUserAccountRequest getUserAccountRequest = new GetUserAccountRequest();
        getUserAccountRequest.setUserId("test");
        assertThrows(UserDoesntExistException.class, () -> userServices.getUserAccount(getUserAccountRequest));

    }

    @Test
    void createAccount_DeleteAccount() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        assertEquals(1, users.count());


        DeleteUserAccountRequest request = new DeleteUserAccountRequest();
        request.setUserId(response.getId());
        request.setPassword("Password1!");
        userServices.deleteUserAccount(request);
        assertEquals(0, users.count());
    }

    @Test
    void attemptToDeleteAccountWithUnregisteredUserThrowsUserDoesntExistException() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        assertEquals(1, users.count());


        DeleteUserAccountRequest request = new DeleteUserAccountRequest();
        request.setUserId("test");
        request.setPassword("Password1!");
        assertThrows(UserDoesntExistException.class, ()-> userServices.deleteUserAccount(request));
    }


    @Test
    void attemptToDeleteAccountWithAnIncorrectPasswordThrowsIncorrectPasswordException() {
        RegisterUserRequest register = new RegisterUserRequest();
        register.setEmail("bolajidurodola@gmail.com");
        register.setPassword("Password1!");
        register.setFirstName("Abolaji");
        register.setLastName("Durodola");
        register.setUsername("lord_boj");
        RegisterUserResponse response = authServices.register(register);
        assertEquals(1, users.count());


        DeleteUserAccountRequest request = new DeleteUserAccountRequest();
        request.setUserId(response.getId());
        request.setPassword("Password");
        assertThrows(IncorrectUserPasswordException.class, ()-> userServices.deleteUserAccount(request));
    }
}