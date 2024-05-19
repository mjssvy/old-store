package com.project.shopapp.service;


import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.ExpiredTokenException;
import com.project.shopapp.models.Token;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.TokenRepository;
import com.project.shopapp.components.JwtTokenUtils;

import com.project.shopapp.services.token.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtTokenUtils jwtTokenUtil;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRefreshToken_WhenTokenExistsAndNotExpired_ShouldReturnNewToken() throws Exception {
        // Arrange
        String refreshToken = "valid-refresh-token";
        User user = new User();
        user.setId(1L);
        Token existingToken = new Token();
        existingToken.setRefreshToken(refreshToken);
        existingToken.setRefreshExpirationDate(LocalDateTime.now().plusMinutes(10)); // future time
        existingToken.setUser(user);

        String newJwtToken = "new-jwt-token";
        when(tokenRepository.findByRefreshToken(refreshToken)).thenReturn(existingToken);
        when(jwtTokenUtil.generateToken(user)).thenReturn(newJwtToken);

        // Act
        Token refreshedToken = tokenService.refreshToken(refreshToken, user);

        // Assert
        assertNotNull(refreshedToken);
        assertEquals(newJwtToken, refreshedToken.getToken());
        assertNotEquals(refreshToken, refreshedToken.getRefreshToken());
        //verify(tokenRepository, times(1)).save(refreshedToken); // Ensure save() is called
    }

    @Test
    void testRefreshToken_WhenTokenDoesNotExist_ShouldThrowDataNotFoundException() {
        // Arrange
        String refreshToken = "non-existent-refresh-token";
        User user = new User();

        when(tokenRepository.findByRefreshToken(refreshToken)).thenReturn(null);

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> tokenService.refreshToken(refreshToken, user));
    }

    @Test
    void testRefreshToken_WhenTokenExpired_ShouldThrowExpiredTokenException() {
        // Arrange
        String refreshToken = "expired-refresh-token";
        User user = new User();
        Token expiredToken = new Token();
        expiredToken.setRefreshToken(refreshToken);
        expiredToken.setRefreshExpirationDate(LocalDateTime.now().minusMinutes(10)); // past time

        when(tokenRepository.findByRefreshToken(refreshToken)).thenReturn(expiredToken);

        // Act & Assert
        assertThrows(ExpiredTokenException.class, () -> tokenService.refreshToken(refreshToken, user));
        verify(tokenRepository, times(1)).delete(expiredToken);
    }

    // Add more test cases for the addToken method if needed
}
