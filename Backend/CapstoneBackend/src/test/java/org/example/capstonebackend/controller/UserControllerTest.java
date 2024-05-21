package org.example.capstonebackend.controller;

import jakarta.inject.Inject;
import org.example.capstonebackend.components.UserTestUtilities;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.service.UserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(UserController.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Inject
    private UserTestUtilities userTestUtilities;

//CREATE
    //create user
        //happy path
    @Test
    public void testAddUser() throws Exception {
        when(userService.addUser(any(User.class))).thenReturn(userTestUtilities.mockUser);

        ResponseEntity<User> response = userController.addUser(userTestUtilities.mockUser);

        verify(userService).addUser(any(User.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userTestUtilities.mockUser, response.getBody());
    }

        //sad path

//READ
    //get user by id
        //happy path
    @Test
    public void testGetUserById() throws Exception {
        int id = 1;
        when(userService.getUserById(id)).thenReturn(userTestUtilities.mockUser);

        mockMvc.perform(get("users/{id}", id))
                .andExpect(status().isOk());

        verify(userService).getUserById(id);
    }



//UPDATE



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
