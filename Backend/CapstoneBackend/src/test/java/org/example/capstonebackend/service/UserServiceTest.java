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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.management.Query.eq;
import static org.example.capstonebackend.components.UserTestUtilities.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        when(userRepository.save(ArgumentMatchers.eq(mockUser))).thenReturn(mockUser);

        User result = userService.addUser((mockUser));

        assertEquals(mockUser, result);
        verify(userRepository).save(mockUser);
    }

        //sad path
    @Test
    public void testAddUser_AlreadyExists() throws Exception {
        when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.save(any())).thenReturn(null);
        when(userRepository.findById(any())).thenReturn(Optional.of(mockUser));

        assertThrows(Exception.class, () -> {
            userService.addUser(mockUser);
        });

        verify(userRepository, times(0)).save(mockUser);
    }

//READ
    //get user by id
        //happy path
    @Test
    public void testGetUserById() throws Exception {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById(mockUser.getId());

        assertEquals(mockUser, result);
        verify(userRepository).findById(anyInt());
    }

        //sad path
    @Test
    public void testGetUserById_UserNotFound() throws Exception {
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            userService.getUserById(mockUser.getId());
        });

        verify(userRepository).findById(mockUser.getId());
    }

    //get user by email
    @Test
    public void testGetUserByEmail() throws Exception {
        //arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));

        //act
        User result = userService.getUserByEmail(mockUser.getEmail());

        //assert
        assertEquals(mockUser, result);
        verify(userRepository).findByEmail(anyString());
    }

        //sad path
    @Test
    public void testGetUserByEmail_NotFound() throws Exception {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            userService.getUserByEmail(mockUser.getEmail());
        });

        verify(userRepository).findByEmail(mockUser.getEmail());
    }

    //get all users
        //happy path
    @Test
    public void testGetAllUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User());
        mockUsers.add(new User());
        mockUsers.add(new User());

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> allUsers = userService.getAllUsers();

        assertEquals(3, allUsers.size());
        verify(userRepository).findAll();
    }

//UPDATE
    //update user
        //happy path
    @Test
    public void testUpdateUser() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        User updatedUser = userService.updateUser(1, mockUser);

        assertNotNull(updatedUser);
        assertEquals(mockUser, updatedUser);
        verify(userRepository).findById(1);
    }

        //sad path
    @Test
    public void testUpdateUser_UserNotFound() throws Exception {
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            userService.getUserById(mockUser.getId());
        });

        assertEquals("User with id " + mockUser.getId() + " not found", exception.getMessage());
    }

//DELETE
    //delete user
        //happy path
    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(anyInt());

        userService.deleteUser(mockUser.getId());

        verify(userRepository).deleteById(mockUser.getId());
    }
}
