package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import net.bytebuddy.matcher.ElementMatchers;
import org.example.capstonebackend.components.UserTestUtilities;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.example.capstonebackend.service.UserService;
import org.example.capstonebackend.service.UserServiceTest;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.example.capstonebackend.components.UserTestUtilities.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private UserService userService;




//CREATE
    //create user
        //happy path
    @Test
    public void testAddUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String userToJson = objectMapper.writeValueAsString(mockUser);

        when(userService.addUser(any(User.class))).thenReturn(mockUser);

        ResultActions resultActions = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToJson));
        resultActions.andExpect(status().isOk());
        compareJsonOutput(resultActions, mockUser);

        verify(userService).addUser(any(User.class));
    }

        //sad path
    @Test
    public void testAddUser_UserExists() throws Exception {
        when(userService.addUser(any())).thenThrow(new Exception("User with id " + mockUser.getUserId() + " already exists"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToJson(mockUser)))
                .andExpect(status().isBadRequest());

        verify(userService).addUser(any());
    }

//READ
    //get user by id
        //happy path
    @Test
    public void testGetUserById() throws Exception {
        int id = 1;
        when(userService.getUserById(id)).thenReturn(mockUser);

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk());

        verify(userService).getUserById(id);
    }

        //sad path
    @Test
    public void testGetUserById_IdNotFound() throws Exception {
        when(userService.getUserById(mockUser.getUserId())).thenThrow(new Exception("User with id " + mockUser.getUserId() + " not found"));

        mockMvc.perform(get("/users/{id}", mockUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService).getUserById(mockUser.getUserId());
    }

    //get user by email
        //happy path
    @Test
    public void testGetUserByEmail() throws Exception {
        String email = "joey_doughy@test.com";

        when(userService.getUserByEmail(email)).thenReturn(mockUser);

        ResultActions resultActions = mockMvc.perform(get("/users/email/{email}", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToJson(mockUser)));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.username", is("Joe Doe")));

        verify(userService).getUserByEmail(email);
    }
        //sad path
        @Test
        public void testGetUserByEmail_UserNotFound() throws Exception {

            String badEmail = "notfound@gmail.com";
            when(userService.getUserByEmail(badEmail)).thenThrow(new Exception("User with email " + badEmail + " does not exist"));

            ResultActions resultActions = mockMvc.perform(get("/users/email/{email}", badEmail)
                    .contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(status().isNotFound());

            verify(userService).getUserByEmail(badEmail);
        }

    //get all users
        //happy path
    @Test
    public void testGetAllUsers() throws Exception {
        List<User> mockUsers = Arrays.asList(mockUser, mockUser);

        when(userService.getAllUsers()).thenReturn(mockUsers);

        ObjectMapper objectMapper = new ObjectMapper();
        String mockUsersJson = objectMapper.writeValueAsString(mockUsers);

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockUsersJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockUsers.size()));

        verify(userService).getAllUsers();
    }

//UPDATE
    //update user
        //happy path
    @Test
    public void testUpdateUser() throws Exception {
        int id = 1;

        User updatedUser = new User();
        updatedUser.setUserId(1);
        updatedUser.setFirstName("Updated Joe");
        updatedUser.setLastName("Doe");
        updatedUser.setDateOfBirth("02/01/1992");
        updatedUser.setEmail("updatedJoey@test.com");
        updatedUser.setPassword("updatedPassword");

        when(userService.updateUser(eq(id), any(User.class))).thenReturn(updatedUser);

        ResultActions resultActions = mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToJson(mockUser)));

        resultActions.andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.firstName", ElementMatchers.is("Updated Joe")))
                .andExpect((ResultMatcher) jsonPath("$.lastName", ElementMatchers.is("Doe")))
                .andExpect((ResultMatcher) jsonPath("$.dateOfBirth", ElementMatchers.is("02/01/1992")))
                .andExpect((ResultMatcher) jsonPath("$.email", ElementMatchers.is("updatedJoey@test.com")))
                .andExpect((ResultMatcher) jsonPath("$.password", ElementMatchers.is("updatedPassword")));

        verify(userService).updateUser(eq(id), any(User.class));
    }

        //sad path
    @Test
    public void TestUpdateUser_IdDoesNotExist() throws Exception {
        when(userService.updateUser(anyInt(), any(User.class))).thenThrow(new Exception("User with id " + mockUser.getUserId() + " not found"));

        mockMvc.perform(put("/users/{id}", mockUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToJson(mockUser)))
                .andExpect(status().isNotFound());

        verify(userService).updateUser(any(Integer.class), any(User.class));
    }


//DELETE
    //delete user
    @Test
    public void testDeleteUser() throws Exception {
        int id = 1;

        doNothing().when(userService).deleteUser(anyInt());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());

        verify(userService).deleteUser(id);
    }
}
