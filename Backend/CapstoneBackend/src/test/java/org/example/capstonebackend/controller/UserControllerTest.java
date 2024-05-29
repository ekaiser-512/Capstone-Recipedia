package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.DTO.UserUpdateDTO;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.example.capstonebackend.components.UserTestUtilities.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserService userService;


    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }


//READ
    //get user by id
        //happy path
@Test
public void testGetUserById() throws Exception {
    Integer userId = 1;
    User user = new User();
    user.setUserId(userId);
    user.setEmail("test@example.com");

    when(userService.getUserById(userId)).thenReturn(user);

    mockMvc.perform(get("/users/{id}", userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.email").value("test@example.com"));
}

        //sad path
    @Test
    public void testGetUserById_IdNotFound() throws Exception {
        Integer userId = 1;

        when(userService.getUserById(userId)).thenThrow(new RuntimeException("User with id " + userId + " not found"));

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isNotFound());

        verify(userService).getUserById(any());
    }

    //get user by email
        //happy path
    @Test
    public void testGetUserByEmail() throws Exception {
//        String email = "test@example.com";
//        User user = new User();
//        user.setEmail(email);

        when(userService.getUserByEmail(mockUser.getEmail())).thenReturn(mockUser);

        mockMvc.perform(get("/users/email/{email}", mockUser.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(mockUser.getEmail()));
    }
        //sad path
        @Test
        public void testGetUserByEmail_UserNotFound() throws Exception {
            String email = "test@example.com";

            when(userService.getUserByEmail(email)).thenThrow(new RuntimeException("User with email " + email + " not found"));

            mockMvc.perform(get("/users/email/{email}", email))
                    .andExpect(status().isNotFound());

            verify(userService).getUserByEmail(any());
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
    public void testUpdateUser_HappyPath() throws Exception {
        User updatedUser = new User();
        updatedUser.setUserId(1);
        updatedUser.setEmail("newemail@example.com");
        updatedUser.setFirstName("New");
        updatedUser.setLastName("Name");

        when(userService.updateUser(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("newemail@example.com"))
                .andExpect(jsonPath("$.data.firstName").value("New"))
                .andExpect(jsonPath("$.data.lastName").value("Name"));

        verify(userService).updateUser(any());
    }


        //sad path
        @Test
        public void testUpdateUser_NotFound() throws Exception {
            when(userService.updateUser(any(User.class)))
                    .thenThrow(new RuntimeException("User with email " + mockUser.getEmail() + " not found"));

            mockMvc.perform(put("/users/{id}", 55)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(mockUser)))
                    .andExpect(status().isBadRequest());

            verify(userService).updateUser(any());
        }

//DELETE
    //delete user
@Test
public void testDeleteUserById() throws Exception {
    Integer userId = 1;

    doNothing().when(userService).deleteUser(userId);

    mockMvc.perform(delete("/users/{id}", userId))
            .andExpect(status().isOk())
            .andExpect(content().string("User with id " + userId + " has been deleted."));
}

    @Test
    public void testDeleteUserById_SadPath() throws Exception {
        Integer userId = 1;
        String errorMessage = "User with id " + userId + " not found";

        doThrow(new RuntimeException(errorMessage)).when(userService).deleteUser(userId);

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(errorMessage));
    }
}
