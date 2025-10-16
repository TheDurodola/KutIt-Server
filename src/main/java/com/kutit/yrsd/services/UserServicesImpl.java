package com.kutit.yrsd.services;

import com.kutit.yrsd.data.models.Entry;
import com.kutit.yrsd.data.models.User;
import com.kutit.yrsd.data.repositories.Entries;
import com.kutit.yrsd.data.repositories.Users;
import com.kutit.yrsd.dtos.requests.*;
import com.kutit.yrsd.dtos.responses.*;
import com.kutit.yrsd.exceptions.*;
import com.kutit.yrsd.utils.PasswordHasher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kutit.yrsd.utils.Mappers.*;
import static com.kutit.yrsd.utils.Validator.validate;

@Slf4j
@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    Users users;

    @Autowired
    Entries entries;


    @Override
    public CreateUserEntryResponse createUserEntry(CreateUserEntryRequest request) {
        validate(request);
        User user = users.findById(request.getUserId()).orElseThrow(()-> new UnauthorizedUserException("User doesn't exist"));
        return mapEntryToCreateUserEntryResponse(user, entries.save(mapCreateUserEntryRequestToEntry(request, user)));
    }

    @Override
    public GetUserEntryResponse getUserEntry(GetUserEntryRequest request) {
        return mapEntryToGetUserEntryResponse(entries.findById(request.getEntryId()).orElseThrow(()-> new EntryDoesntExistException("Entry doesn't exist")));
    }

    @Override
    public GetAllUserEntriesResponse getAllUserEntries(GetAllUserEntriesRequest request) {
        GetAllUserEntriesResponse extractedEntries = new GetAllUserEntriesResponse();
        if (users.findById(request.getUserId()).isPresent()){
            User user = users.findById(request.getUserId()).get();
            List<Entry> extracted = entries.findByUser(user);
            if(extracted.isEmpty()) throw  new EntriesDontExistsException("Entries don't exist");
            extractedEntries.setEntries(extracted);
            return extractedEntries;
        }

        throw new UserDoesntExistException("No User found");
    }

    @Override
    public DeleteUserEntryResponse deleteUserEntry(DeleteUserEntryRequest request) {
        DeleteUserEntryResponse response = new DeleteUserEntryResponse();
        if (users.findById(request.getUserId()).isPresent()){
            verifyUserEntryRelation(request);
            if(entries.findById(request.getEntryId()).isPresent()){
                entries.deleteById(request.getEntryId());
                response.setMessage("Entry deleted successfully");
                return response;
            }
        }
        throw new UserDoesntExistException("No User found");
    }

    @Override
    public UpdateUserEntryResponse updateUserEntry(UpdateUserEntryRequest request) {
        UpdateUserEntryResponse response = new UpdateUserEntryResponse();
        if (users.findById(request.getUserId()).isPresent()) {
            verifyUserEntryRelation(request);
            if (entries.findById(request.getEntryId()).isPresent()) {
                Entry entry = entries.findById(request.getEntryId()).get();
                entry.setOriginalLink(request.getOriginalLink());
                Entry updatedEntry = entries.save(entry);
                log.info("Updated Entry: ");
                mapEntryToUpdatedUserEntryResponse(response, updatedEntry);
                return response;
            }
        }
        throw new UserDoesntExistException("No User found");
    }

    private static void mapEntryToUpdatedUserEntryResponse(UpdateUserEntryResponse response, Entry updatedEntry) {
        response.setEntryId(updatedEntry.getId());
        response.setMessage("Entry updated successfully");
        response.setOriginalLink(updatedEntry.getOriginalLink());
        response.setShortenedLink(updatedEntry.getShortenedLink());
    }

    @Override
    public UpdateUserAccountResponse updateUserAccount(UpdateUserAccountRequest request) {
        if (users.findById(request.getId()).isPresent()){
            User user = getUser(request);
            User updatedUser = users.save(user);
            return mapUserToUpdateUserAccountResponse(updatedUser);
        }
        throw new UserDoesntExistException("No User found");
    }

    private User getUser(UpdateUserAccountRequest request) {
        User user = users.findById(request.getId()).get();
        user.setFirstname(request.getFirstName());
        user.setLastname(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        if(PasswordHasher.matches(request.getOldPassword(), user.getPassword())){
            user.setPassword(PasswordHasher.hashPassword(request.getNewPassword()));
        }else throw new IncorrectUserPasswordException("Old password is incorrect");
        return user;
    }

    private UpdateUserAccountResponse mapUserToUpdateUserAccountResponse(User updatedUser) {
        UpdateUserAccountResponse response = new UpdateUserAccountResponse();
        response.setFirstName(updatedUser.getFirstname());
        response.setLastName(updatedUser.getLastname());
        response.setEmail(updatedUser.getEmail());
        response.setUsername(updatedUser.getUsername());
        response.setMessage("User account updated successfully");
        return response;
    }

    @Override
    public GetUserAccountResponse getUserAccount(GetUserAccountRequest request) {
        if (users.findById(request.getUserId()).isPresent()){
            User user = users.findById(request.getUserId()).get();
            return mapUserToGetUserAccountResponse(user);
        }
        throw new UserDoesntExistException("No User found");
    }

    private GetUserAccountResponse mapUserToGetUserAccountResponse(User user) {
        GetUserAccountResponse response = new GetUserAccountResponse();
        response.setFirstName(user.getFirstname());
        response.setLastName(user.getLastname());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        return response;
    }

    @Override
    public DeleteUserAccountResponse deleteUserAccount(DeleteUserAccountRequest request) {
        return null;
    }


    private void verifyUserEntryRelation(DeleteUserEntryRequest request) {
        User user;
        Entry entry;
        if(users.findById(request.getUserId()).isPresent()){
            user = users.findById(request.getUserId()).get();
        }else throw new UserDoesntExistException("This user doesn't exist");
        if(entries.findById(request.getEntryId()).isPresent()){
            entry = entries.findById(request.getEntryId()).get();
        }else throw new EntryDoesntExistException("This entry doesn't exist");

        if(!user.getId().equals(entry.getUser().getId())) throw new UnauthorizedUserException("This Entry doesn't belong to this User");

    }

    private void verifyUserEntryRelation(UpdateUserEntryRequest request) {
        User user;
        Entry entry;
        if(users.findById(request.getUserId()).isPresent()){
            user = users.findById(request.getUserId()).get();
        }else throw new UserDoesntExistException("This user doesn't exist");
        if(entries.findById(request.getEntryId()).isPresent()){
            entry = entries.findById(request.getEntryId()).get();
        }else throw new EntryDoesntExistException("This entry doesn't exist");

        if(!user.getId().equals(entry.getUser().getId())) throw new UnauthorizedUserException("This Entry doesn't belong to this User");

    }
}
