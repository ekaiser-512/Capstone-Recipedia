package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.example.capstonebackend.components.AuthTestUtilities;
import org.example.capstonebackend.model.Auth;
import org.example.capstonebackend.repository.IAuthRepository;
import org.example.capstonebackend.service.AuthService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.example.capstonebackend.components.AuthTestUtilities.*;
import static org.example.capstonebackend.components.AuthTestUtilities.authToJson;
import static org.example.capstonebackend.components.AuthTestUtilities.email;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({AuthController.class, AuthTestUtilities.class})
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @InjectMocks
    private AuthTestUtilities authTestUtilities;


    //CREATE

    //add Auth
    //happy path
    @Test
    void testUserSignup() throws Exception {
        //arrange
        when(authService.userSignup(any(Auth.class))).thenReturn(mockAuth);

        //act
        ResponseEntity<Auth> response = authController.userSignup(mockAuth);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAuth, response.getBody());
        verify(authService).userSignup(any(Auth.class));
    }

    //sad path
    @Test
    void testAddAuth_UserExists() throws Exception {
        //arrange
        when(authService.userSignup(any())).thenThrow(new Exception("user with email " + mockAuth.getEmail() + " already exists"));

        //act
        mockMvc.perform(post("/auths")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authToJson(mockAuth)))
                .andExpect(status().isBadRequest());

        //assert
        verify(authService).userSignup(any());

    }

    @Test
    void testUserLogin() throws Exception {
        when(authService.getAuthByEmail(mockAuth.getEmail())).thenReturn(mockAuth);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authToJson(mockAuth)))
            .andExpect(status().isOk());

        verify(authService).getAuthByEmail(anyString());
    }



//READ
    //get auth by ID
        //happy path
    @Test
    public void testGetAuthById() throws Exception {
        //arrange
        int id = 1;
        when(authService.getAuthById((id))).thenReturn(mockAuth);

        //act
        mockMvc.perform(get("/auths/{id}", id))
                .andExpect(status().isOk()); //status 200

        //assert
        verify(authService).getAuthById(id);

    }

    //sad path
    @Test
    public void testGetAuthById_IdNotFound() throws Exception {
        //arrange
        when(authService.getAuthById(mockAuth.getId())).thenThrow(new Exception("Auth with id " + mockAuth.getId() + " not found"));

        //act
        mockMvc.perform(get("/auths/{id}", mockAuth.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //assert
        verify(authService).getAuthById(mockAuth.getId());
    }

    //get auth by email
    //happy path
    @Test
    public void testGetAuthByEmail() throws Exception {
        //arrange
        when(authService.getAuthByEmail(email)).thenReturn(mockAuth);

        //act
        ResultActions resultActions = mockMvc.perform(get("/auths/email/{email}", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(authToJson(mockAuth)));
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is(email)));

        //assert
        verify(authService).getAuthByEmail(email);
    }

    //sad path
    @Test
    public void testGetAuthByEmail_EmailNotFound() throws Exception {
        //arrange
        String badEmail = "notfound@test.com";
        when(authService.getAuthByEmail(badEmail)).thenThrow(new Exception("User with email " + badEmail + " does not exist"));

        //act
        ResultActions resultActions = mockMvc.perform(get("/auths/email/{email}", badEmail)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());

        //assert
        verify(authService).getAuthByEmail(badEmail);
    }

    //UPDATE
    //update auth
    //happy path
    @Test
    public void testUpdateAuth() throws Exception {
        //arrange
        int id = 1;
        Auth updatedAuth = new Auth();
        updatedAuth.setEmail("Joey_Doe");
        updatedAuth.setPassword("joey.doeghy");

        when(authService.updateAuth(eq(id), any(Auth.class))).thenReturn(updatedAuth);

        //act
        ResultActions resultActions = mockMvc.perform(put("/auths/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(authToJson(mockAuth)));
        resultActions.andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.email", is("Joey_Doe")))
                .andExpect((ResultMatcher) jsonPath("$.password", is("joey.doeghy")));

        //assert
        verify(authService).updateAuth(eq(id), any(Auth.class));
    }

    //sad path
    @Test
    public void testUpdateAuth_IdDoesNotExist() throws Exception {
        //arrange
        when(authService.updateAuth(anyInt(), any(Auth.class))).thenThrow(new Exception("Auth with id " + mockAuth.getId() + " not found"));
        //act
        mockMvc.perform(put("/auth/{id}", mockAuth.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authToJson(mockAuth)))
                .andExpect(status().isNotFound());

        //assert
        verify(authService).updateAuth(any(Integer.class), any(Auth.class));
    }
}