package com.example.jwt_auth_api.responses;

public class LoginResponse {
    private String token;
    private long expiresIn;

    public LoginResponse setToken(String token){
        this.token=token;
        return this;
    }
    public LoginResponse setexpiresIn(Long expiresIn){
        this.expiresIn=expiresIn;
        return this;
    }

    @Override
    public String toString(){
        return "LoginResponse{" +
        "token='" + token + '\'' +
        ", expiresIn=" + expiresIn +
        '}';
    }

    public String getToken(){
        return token;
    }
    public long getExpiresIn(){
        return expiresIn;
    }

}
