package com.example.jwt_auth_api.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.*;

//Defines User Database Entity
@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private Long id;
    @Column(nullable=false,unique=true)
    private String username;
    @Column(nullable=false)
    private String password;
    @Column(nullable=false,unique=true)
    private String email;

    @Column(nullable=false)
    private boolean enabled;

    public User(){
    }

    public User (String username, String password, String email, boolean enabled){
        this.email=email;
        this.username=username;
        this.password=password;
        this.enabled=enabled;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }

    public String getEmail(){
        return email;
    }
    public User setEmail(String email){
        this.email=email;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public String getUsername(){
        return username;
    }
    public User setUsername(String username){
        this.username=username;
        return this;
    }

    public String getPassword(){
        return password;
    }
    public User setPassword(String password){
        this.password=password;
        return this;
    }

    public User setEnabled(boolean enabled){
        this.enabled=enabled;
        return this;
    }   
    public boolean getEnabled(){
        return enabled;
    } 
}
