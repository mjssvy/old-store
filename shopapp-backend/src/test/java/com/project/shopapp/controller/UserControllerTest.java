package com.project.shopapp.controller;

import com.project.shopapp.controllers.UserController;
import com.project.shopapp.models.Token;
import com.project.shopapp.models.User;
import com.project.shopapp.models.Role;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.responses.user.LoginResponse;
import com.project.shopapp.services.token.ITokenService;
import com.project.shopapp.services.user.IUserService;
import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest
public class UserControllerTest {

    @Mock
    private IUserService userService;

    @Mock
    private ITokenService tokenService;

    @Mock
    private LocalizationUtils localizationUtils;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() throws Exception {
        // Arrange
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setPhoneNumber("0949905592");
        userLoginDTO.setPassword("123456789");
        userLoginDTO.setRoleId(1L);

        User userDetail = User.builder()
                .id(1L)
                .phoneNumber("0949905592")
                .role(new Role(1L, Role.USER))
                .build();

        Token jwtToken = new Token();
        jwtToken.setToken("jwt-token");
        jwtToken.setTokenType("Bearer");
        jwtToken.setRefreshToken("refresh-token");

        // Mock phương thức `userService.login` để trả về một đối tượng Token
        when(userService.login(eq(userLoginDTO.getPhoneNumber()), eq(userLoginDTO.getPassword()), eq(userLoginDTO.getRoleId())))
                .thenReturn("jwtToken");

        // Mock phương thức `userService.getUserDetailsFromToken` để trả về đối tượng User
        when(userService.getUserDetailsFromToken(anyString())).thenReturn(userDetail);

        // Mock phương thức `tokenService.addToken` để trả về jwtToken
        when(tokenService.addToken(any(User.class), anyString(), anyBoolean())).thenReturn(jwtToken);

        // Mock phương thức `localizationUtils.getLocalizedMessage` để trả về chuỗi "Login successfully"
        when(localizationUtils.getLocalizedMessage(anyString())).thenReturn("Login successfully");

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mocked User Agent");

        // Act
        ResponseEntity<ResponseObject> response = userController.login(userLoginDTO, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        LoginResponse loginResponse = (LoginResponse) response.getBody().getData();
        assertNotNull(loginResponse); // Ensure loginResponse is not null
        assertEquals("jwt-token", loginResponse.getToken());
        assertEquals("Bearer", loginResponse.getTokenType());
        assertEquals("refresh-token", loginResponse.getRefreshToken());
        assertEquals("0949905592", loginResponse.getUsername());
        assertEquals(List.of("ROLE_USER"), loginResponse.getRoles());

        // Verify method invocations
        verify(userService, times(1)).login(eq(userLoginDTO.getPhoneNumber()), eq(userLoginDTO.getPassword()), eq(userLoginDTO.getRoleId()));
        verify(tokenService, times(1)).addToken(any(User.class), anyString(), anyBoolean());
    }

    @Test
    void testGetUserDetailsFromToken() throws Exception {
        // Arrange
        User userDetail = User.builder()
                .id(1L)
                .phoneNumber("0949905592")
                .role(new Role(1L, Role.USER))
                .build();
        when(userService.getUserDetailsFromToken(anyString())).thenReturn(userDetail);

        // Act
        User userDetails = userService.getUserDetailsFromToken("dummy-token");

        // Assert
        assertNotNull(userDetails);
        assertEquals("0949905592", userDetails.getPhoneNumber());
        assertEquals(1L, userDetails.getRole().getId());
        assertEquals(Role.USER, userDetails.getRole().getName());

        // Verify method invocations
        verify(userService, times(1)).getUserDetailsFromToken(anyString());
    }

    @Test
    void testAddToken() {
        // Arrange
        Token jwtToken = new Token();
        jwtToken.setToken("jwt-token");
        jwtToken.setTokenType("Bearer");
        jwtToken.setRefreshToken("refresh-token");
        when(tokenService.addToken(any(User.class), anyString(), anyBoolean())).thenReturn(jwtToken);

        // Act
        Token addedToken = tokenService.addToken(new User(), "dummy-token", true);

        // Assert
        assertNotNull(addedToken);
        assertEquals("jwt-token", addedToken.getToken());
        assertEquals("Bearer", addedToken.getTokenType());
        assertEquals("refresh-token", addedToken.getRefreshToken());

        // Verify method invocations
        verify(tokenService, times(1)).addToken(any(User.class), anyString(), anyBoolean());
    }
}