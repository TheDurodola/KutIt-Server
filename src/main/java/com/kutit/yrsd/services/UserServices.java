package com.kutit.yrsd.services;

import com.kutit.yrsd.dtos.requests.*;
import com.kutit.yrsd.dtos.responses.*;

public interface UserServices {
    CreateUserEntryResponse createUserEntry(CreateUserEntryRequest request);
    GetUserEntryResponse getUserEntry(GetUserEntryRequest request);
    GetAllUserEntriesResponse getAllUserEntries(GetAllUserEntriesRequest request);
    DeleteUserEntryResponse deleteUserEntry(DeleteUserEntryRequest request);
    UpdateUserEntryResponse updateUserEntry(UpdateUserEntryRequest request);
    UpdateUserAccountResponse updateUserAccount(UpdateUserAccountRequest request);
    GetUserAccountResponse getUserAccount(GetUserAccountRequest request);
    DeleteUserAccountResponse deleteUserAccount(DeleteUserAccountRequest request);
}
