package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.example.capstonebackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.example.capstonebackend.components.UserTestUtilities.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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

        when(userService.getUserByEmail(anyString())).thenReturn(mockUser);

        ResultActions resultActions = mockMvc.perform(get("/users/email/{email}", mockUser.getEmail()));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(mockUser.getEmail())))
                .andExpect(jsonPath("$.firstName", is("Joe")))
                .andExpect(jsonPath("$.lastName", is("Doe")));

        verify(userService).getUserByEmail(anyString());
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
                .andExpect(jsonPath("$.firstName", is("Updated Joe")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.dateOfBirth", is("02/01/1992")))
                .andExpect(jsonPath("$.email", is("updatedJoey@test.com")))
                .andExpect(jsonPath("$.password", is("updatedPassword")));

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
