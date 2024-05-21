package org.example.capstonebackend.service;

import jakarta.inject.Inject;
import org.example.capstonebackend.components.AuthTestUtilities;
import org.example.capstonebackend.controller.AuthController;
import org.example.capstonebackend.model.Auth;
import org.example.capstonebackend.repository.IAuthRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.capstonebackend.components.AuthTestUtilities.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthServiceTest {

    @MockBean
    private IAuthRepository authRepository;

    @Autowired
    private AuthService authService;

    @Inject
    private AuthTestUtilities authTestUtilities;

    //CREATE

    //add Auth
        //happy path
    @Test
    public void testUserSignup() throws Exception {
        //arrange
        when(authRepository.save(eq(mockAuth))).thenReturn(mockAuth);

        //act
        Auth result = authService.userSignup(mockAuth);

        //assert
        assertEquals(mockAuth, result);
        verify(authRepository).save(mockAuth);
    }

        //sad path
    @Test
    public void testAddAuth_AuthExists() throws Exception {
        //arrange
        when(authRepository.existsById(any())).thenReturn(true);
        when(authRepository.save(any())).thenReturn(null);
        when(authRepository.findByEmail(any())).thenReturn(Optional.of(mockAuth));

        assertThrows(Exception.class, () -> {
            authService.userSignup(mockAuth);
        });

        verify(authRepository, times(0)).save(mockAuth);
    }

//READ
    //get auth by id
        //happy path
    @Test
    public void testGetAuthById() throws Exception {
        when(authRepository.findById(anyInt())).thenReturn(Optional.of(mockAuth));

        Auth result = authService.getAuthById(mockAuth.getId());

        assertEquals(mockAuth, result);
        verify(authRepository).findById(anyInt());
    }
        //sad path
    @Test
    public void testGetAuthById_IdNotFound() throws Exception {
        when(authRepository.findById(mockAuth.getId())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            authService.getAuthById(mockAuth.getId());
        });

        verify(authRepository).findById(mockAuth.getId());
    }

    //get all auths
        //happy path
    @Test
    public void testGetAllAuths() {
        List<Auth> mockAuths = new ArrayList<>();
        mockAuths.add(new Auth());
        mockAuths.add(new Auth());
        mockAuths.add(new Auth());

        when(authRepository.findAll()).thenReturn(mockAuths);

        List<Auth> allAuths = authService.getAllAuths();

        assertEquals(3, allAuths.size());
        verify(authRepository).findAll();
    }

//DELETE
    //delete auth
    @Test
    public void testDeleteAuth() {
        doNothing().when(authRepository).deleteById(anyInt());

        authService.deleteAuth(mockAuth.getId());

        verify(authRepository).deleteById(mockAuth.getId());
    }
}
