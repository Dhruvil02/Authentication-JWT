package com.example.jwt_auth_api.services;

import com.example.jwt_auth_api.model.User;
import com.example.jwt_auth_api.repositories.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    @Transactional
    public boolean deleteUserByUsername (String username){
        if(userRepository.existsByUsername(username)){
            userRepository.deleteByUsername(username);
            //userRepository.flush();
            return true;
        }
        return false;
    }
     
}