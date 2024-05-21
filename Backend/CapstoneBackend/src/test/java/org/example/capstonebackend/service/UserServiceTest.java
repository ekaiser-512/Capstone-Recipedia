package org.example.capstonebackend.service;

import jakarta.inject.Inject;
import org.example.capstonebackend.components.UserTestUtilities;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private IUserRepository userRepository;

    @Autowired
    private UserService userService;

    @Inject
    private UserTestUtilities userTestUtilities;


//CREATE
    //create user
        //happy path
    @Test
    public void testAddUser() throws Exception {
        when(userRepository.save(ArgumentMatchers.eq(userTestUtilities.mockUser))).thenReturn(userTestUtilities.mockUser);

        User result = userService.addUser((userTestUtilities.mockUser));

        assertEquals(userTestUtilities.mockUser, result);
        verify(userRepository).save(userTestUtilities.mockUser);
    }

        //sad path

//READ
    //get user by id


//UPDATE



//DELETE
}
