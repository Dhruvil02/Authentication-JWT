package com.example.jwt_auth_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt_auth_api.model.User;
import com.example.jwt_auth_api.dto.RegisterUser;
import com.example.jwt_auth_api.dto.LoginUser;
import com.example.jwt_auth_api.services.AuthenticationService;
import com.example.jwt_auth_api.services.JwtService;
import com.example.jwt_auth_api.responses.LoginResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController (JwtService jwtService, AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
        this.jwtService=jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUser registerUser) {
        //TODO: process PUT request
        if(authenticationService.isUsernameTaken(registerUser.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username alrady exists");
        }
        User registered=authenticationService.signup(registerUser);
        return ResponseEntity.ok(registered);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> putMethodName(@RequestBody LoginUser loginUser) {
        //TODO: process PUT request
        User VerifiedUser = authenticationService.authenticate(loginUser);
        String jwtToken=jwtService.generateToken(VerifiedUser);
        LoginResponse loginResponse =new LoginResponse().setToken(jwtToken).setexpiresIn(jwtService.getExpiration());
        return ResponseEntity.ok(loginResponse);
    }

}
