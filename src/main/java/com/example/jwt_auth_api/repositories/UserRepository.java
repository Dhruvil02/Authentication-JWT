package com.example.jwt_auth_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.jwt_auth_api.model.User;
import java.util.Optional;

// CRUD operations on "User" Entity
@Repository
public interface UserRepository extends JpaRepository <User,Long> {
    
    Optional<User> findByUsername(String username);
    //Useful for validation during registration.
    Boolean existsByUsername(String username);
    //help enforce unique email addresses.
    Boolean existsByEmail(String email);
}
//JpaRepository extends the basic CRUD operations from CrudRepository and adds more advanced capabilities