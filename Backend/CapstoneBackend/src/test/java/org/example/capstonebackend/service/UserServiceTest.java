package org.example.capstonebackend.service;

import org.example.capstonebackend.DTO.UserUpdateDTO;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


//READ
    //get user by id
        //happy path
    @Test
    public void testGetUserById() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById(mockUser.getUserId());

        assertEquals(mockUser, result);
        verify(userRepository).findById(anyInt());
    }

        //sad path
    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(mockUser.getUserId())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.getUserById(mockUser.getUserId()));

        verify(userRepository).findById(mockUser.getUserId());
    }

    //get user by email
    @Test
    public void testGetUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());

        verify(userRepository, times(1)).findByEmail(email);
    }

        //sad path
    @Test
    public void testGetUserByEmail_NotFound() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.getUserByEmail(email));

        assertEquals("User with email " + email + " not found", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(email);
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
        //happy path
@Test
public void testUpdateUser() {
    User existingUser = new User();
    existingUser.setUserId(1);
    existingUser.setEmail("oldemail@example.com");
    existingUser.setFirstName("Old");
    existingUser.setLastName("Name");

    when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(existingUser));
    when(userRepository.save(any(User.class))).thenReturn(existingUser);

    User updatedUser = userService.updateUser(mockUser);

    assertNotNull(updatedUser);
    assertEquals(existingUser.getEmail(), updatedUser.getEmail());
    assertEquals(existingUser.getFirstName(), updatedUser.getFirstName());
    assertEquals(existingUser.getLastName(), updatedUser.getLastName());

    verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
    verify(userRepository, times(1)).save(existingUser);
}


        //sad path
    @Test
    public void testUpdateUser_UserNotFound() {
        when(userRepository.findById(mockUser.getUserId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class,
                () -> userService.getUserById(mockUser.getUserId()));

        assertEquals("User with id " + mockUser.getUserId() + " not found", exception.getMessage());
    }

//DELETE
        //happy path
@Test
public void testDeleteUserById() {
    Integer userId = 1;
    User user = new User();
    user.setUserId(userId);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    assertDoesNotThrow(() -> userService.deleteUser(userId));

    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, times(1)).delete(user);
}

    //sad path
    @Test
    public void testDeleteUserById_NotFound() {
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.deleteUser(userId));

        assertEquals("User with id " + userId + " not found", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).delete(any(User.class));
    }
}
