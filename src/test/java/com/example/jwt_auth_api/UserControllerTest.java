package com.example.jwt_auth_api;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.example.jwt_auth_api.controllers.UserController;
import com.example.jwt_auth_api.model.User;
import com.example.jwt_auth_api.services.*;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
    }

    @Mock
    private UserService userService;

    @Test
    void getUserInfo_positive(){
        //Mock authnetication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        User mockUser = new User ("TestUser", "password","Test@user.com", true);
        //Mock behaviour
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        // Set the mock SecurityContext
        SecurityContextHolder.setContext(securityContext);

        // Call the API method
        ResponseEntity<User> response = userController.getUserInfo();

        // Verify and assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void deleteUser_Successful(){
        //Mock components
        User mockdelUser = new User("TestUser", "password","Test@user.com", true);
        String username = mockdelUser.getUsername();
        //Mock behaviour
        when(userService.deleteUserByUsername(username)).thenReturn(true);
        //TEst Logic
        ResponseEntity<?>response=userController.deleteUser(username);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User '"+username+"' deleted successfully",response.getBody());
    }


}
