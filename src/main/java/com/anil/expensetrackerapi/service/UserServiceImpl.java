package com.anil.expensetrackerapi.service;

import com.anil.expensetrackerapi.entity.User;
import com.anil.expensetrackerapi.entity.UserModel;
import com.anil.expensetrackerapi.exceptions.ItemExistsException;
import com.anil.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.anil.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(UserModel user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ItemExistsException("User is already registered with this email " + user.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        return  userRepository.save(newUser);
    }

    @Override
    public User readUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for id : " + id));
    }

    @Override
    public User updateUser(UserModel user, Long id) {
        User existingUser = readUser(id);

        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword() != null ? user.getPassword() : existingUser.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = readUser(id);
        userRepository.delete(existingUser);
    }

}
