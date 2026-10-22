package com.anil.expensetrackerapi.service;

import com.anil.expensetrackerapi.entity.User;
import com.anil.expensetrackerapi.entity.UserModel;
import org.springframework.stereotype.Service;


public interface UserService {
    User createUser(UserModel user);
    User readUser();
    User updateUser(UserModel user);
    void deleteUser();
    User getLoggedInUser();
}
