package org.example.capstonebackend.service;

import jakarta.inject.Inject;
import org.example.capstonebackend.components.AuthTestUtilities;
import org.example.capstonebackend.model.Auth;
import org.example.capstonebackend.repository.IAuthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(authRepository.existsByEmail(authTestUtilities.mockAuth.getUserEmail())).thenReturn(false);
        when(authRepository.save(eq(authTestUtilities.mockAuth))).thenReturn(authTestUtilities.mockAuth);

        //act
        boolean result = authService.addAuthByEmail(authTestUtilities.mockAuth);

        //assert
        assertTrue(result);
        verify(authRepository).existsByEmail(authTestUtilities.mockAuth.getUserEmail());
        verify(authRepository).save(authTestUtilities.mockAuth);
    }

}
