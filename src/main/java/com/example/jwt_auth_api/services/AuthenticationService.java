package com.example.jwt_auth_api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt_auth_api.dto.LoginUser;
import com.example.jwt_auth_api.dto.RegisterUser;
import com.example.jwt_auth_api.model.User;
import com.example.jwt_auth_api.repositories.UserRepository;

@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService (UserRepository userRepository,
    AuthenticationManager authenticationManager,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
    }

    public User signup(RegisterUser input){
        User user = new User()
                .setUsername(input.getUsername())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));
        
        return userRepository.save(user);
    }

    public User authenticate (LoginUser input){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            input.getUsername(),input.getPassword()));

        return userRepository.findByUsername(input.getUsername()).orElseThrow();
    }

    public boolean isUsernameTaken (String username){
        return userRepository.existsByUsername(username);
    }
}
