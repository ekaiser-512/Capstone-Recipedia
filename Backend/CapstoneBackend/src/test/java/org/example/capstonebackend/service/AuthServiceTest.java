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
    public void testAddAuth() throws Exception {
        //arrange
        when(authRepository.save(eq(authTestUtilities.mockAuth))).thenReturn(authTestUtilities.mockAuth);

        //act
        Auth result = authService.addAuth(authTestUtilities.mockAuth);

        //assert
        assertEquals(authTestUtilities.mockAuth, result);
        verify(authRepository).save(authTestUtilities.mockAuth);
    }

        //sad path
    @Test
    public void testAddAuth_AuthExists() throws Exception {
        //arrange
        when(authRepository.existsById(any())).thenReturn(true);
        when(authRepository.save(any())).thenReturn(null);
        when(authRepository.findByEmail(any())).thenReturn(Optional.of(authTestUtilities.mockAuth));

        assertThrows(Exception.class, () -> {
            authService.addAuth(authTestUtilities.mockAuth);
        });

        verify(authRepository, times(0)).save(authTestUtilities.mockAuth);
    }

//READ
    //get auth by id
        //happy path
    @Test
    public void testGetAuthById() throws Exception {
        when(authRepository.findById(anyInt())).thenReturn(Optional.of(authTestUtilities.mockAuth));

        Auth result = authService.getAuthById(authTestUtilities.mockAuth.getId());

        assertEquals(authTestUtilities.mockAuth, result);
        verify(authRepository).findById(anyInt());
    }
        //sad path
    @Test
    public void testGetAuthById_IdNotFound() throws Exception {
        when(authRepository.findById(authTestUtilities.mockAuth.getId())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            authService.getAuthById(authTestUtilities.mockAuth.getId());
        });

        verify(authRepository).findById(authTestUtilities.mockAuth.getId());
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

        authService.deleteAuth(authTestUtilities.mockAuth.getId());

        verify(authRepository).deleteById(authTestUtilities.mockAuth.getId());
    }
}
