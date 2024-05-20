package org.example.capstonebackend.controller;

import jakarta.inject.Inject;
import org.example.capstonebackend.components.AuthTestUtilities;
import org.example.capstonebackend.model.Auth;
import org.example.capstonebackend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(AuthController.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Inject
    private AuthTestUtilities authTestUtilities;

    //CREATE

    //add Auth
        //happy path
    @Test
    void testAddAuthByEmail() throws Exception {
        //arrange
        when(authService.addAuthByEmail(any(Auth.class))).thenReturn(true);

        //act
        mockMvc.perform(post("/auths/userEmail/{userEmail}", AuthTestUtilities.email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(authTestUtilities.authToJson(authTestUtilities.mockAuth)))
            .andExpect(status().isCreated()) //Expecting 201
            .andExpect(jsonPath("$.email").value(authTestUtilities.mockAuth));
        //assert
        verify(authService).addAuthByEmail(any(Auth.class));
    }
}
