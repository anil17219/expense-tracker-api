package com.anil.expensetrackerapi.service;

import com.anil.expensetrackerapi.entity.User;
import com.anil.expensetrackerapi.entity.UserModel;
import org.springframework.stereotype.Service;


public interface UserService {
    User createUser(UserModel user);
    User readUser(Long id);
    User updateUser(UserModel user, Long id);
    void deleteUser(Long id);
    User getLoggedInUser();
}
