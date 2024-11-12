package com.example.jwt_auth_api.dto;

public class LoginUser {
    private String username;
    private String password;

    //Constructors
    public LoginUser(){}

    public LoginUser (String username, String password){
        this.username=username;
        this.password=password;
    }
    //Getters and setters
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

}
