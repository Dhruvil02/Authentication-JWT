package com.example.jwt_auth_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt_auth_api.model.User;
import com.example.jwt_auth_api.services.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List <User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
    
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        boolean isDeleted = userService.deleteUserByUsername(username);
        if (isDeleted){
            return ResponseEntity.ok("User '"+username+"' deleted successfully");
        }else{
            return ResponseEntity.status(404).body("User '"+username+"' not found");
        }  
    }    
}
