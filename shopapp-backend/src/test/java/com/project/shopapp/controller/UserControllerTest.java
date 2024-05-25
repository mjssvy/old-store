package com.project.shopapp.controller;

import com.project.shopapp.controllers.UserController;
import com.project.shopapp.dtos.*;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidPasswordException;
import com.project.shopapp.models.Token;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.responses.user.LoginResponse;
import com.project.shopapp.responses.user.UserListResponse;
import com.project.shopapp.responses.user.UserResponse;
import com.project.shopapp.services.token.ITokenService;
import com.project.shopapp.services.user.IUserService;
import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.utils.MessageKeys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private IUserService userService;

    @Mock
    private LocalizationUtils localizationUtils;

    @Mock
    private ITokenService tokenService;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateUser_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setRetypePassword("password");

        User user = new User();
        when(userService.createUser(any(UserDTO.class))).thenReturn(user);

        ResponseEntity<ResponseObject> response = userController.createUser(userDTO, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Đăng ký tài khoản thành công", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testCreateUser_PasswordMismatch() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setRetypePassword("differentPassword");

        when(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH))
                .thenReturn("Passwords do not match");

        ResponseEntity<ResponseObject> response = userController.createUser(userDTO, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Passwords do not match", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    private void assertNull(Object data) {
    }

    @Test
    void testCreateUser_ValidationErrors() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setRetypePassword("password");

        FieldError error = new FieldError("userDTO", "password", "Password is required");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(error));

        ResponseEntity<ResponseObject> response = userController.createUser(userDTO, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("[Password is required]", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }


    @Test
    void testGetUserDetails_Success() throws Exception {
        String token = "Bearer jwtToken";
        User user = new User();
        user.setId(1L);
        when(userService.getUserDetailsFromToken(anyString())).thenReturn(user);

        ResponseEntity<ResponseObject> response = userController.getUserDetails(token);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Get user's detail successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateUserDetails_Success() throws Exception {
        Long userId = 1L;
        UpdateUserDTO updatedUserDTO = new UpdateUserDTO();
        String token = "Bearer jwtToken";
        User user = new User();
        user.setId(userId);

        when(userService.getUserDetailsFromToken(anyString())).thenReturn(user);
        when(userService.updateUser(anyLong(), any(UpdateUserDTO.class))).thenReturn(user);

        ResponseEntity<ResponseObject> response = userController.updateUserDetails(userId, updatedUserDTO, token);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Update user detail successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testResetPassword_Success() throws Exception {
        long userId = 1L;
        String newPassword = UUID.randomUUID().toString().substring(0, 5);

        doNothing().when(userService).resetPassword(userId, newPassword);

        ResponseEntity<ResponseObject> response = userController.resetPassword(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Reset password successfully", response.getBody().getMessage());
        assertEquals(newPassword, response.getBody().getData());
    }

    @Test
    void testBlockOrEnable_Success_Enable() throws Exception {
        long userId = 1L;
        int active = 1;

        doNothing().when(userService).blockOrEnable(userId, true);

        ResponseEntity<ResponseObject> response = userController.blockOrEnable(userId, active);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Successfully enabled the user.", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void testBlockOrEnable_Success_Block() throws Exception {
        long userId = 1L;
        int active = 0;

        doNothing().when(userService).blockOrEnable(userId, false);

        ResponseEntity<ResponseObject> response = userController.blockOrEnable(userId, active);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Successfully blocked the user.", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }




}
