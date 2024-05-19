package com.project.shopapp.service;


import com.project.shopapp.components.JwtTokenUtils;
import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.UpdateUserDTO;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.ExpiredTokenException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.TokenRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtils jwtTokenUtil;

    @InjectMocks
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private LocalizationUtils localizationUtils;

    @Mock
    private TokenRepository tokenRepository;



    private UserDTO userDTO;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setPhoneNumber("123456789");
        userDTO.setFullName("Test User");
        userDTO.setPassword("password");
        userDTO.setRoleId(1L);

        role = new Role();
        role.setId(1L);
        role.setName("USER");

        user = new User();
        user.setId(1L);
        user.setPhoneNumber("123456789");
        user.setFullName("Test User");
        user.setPassword("encodedPassword");
        user.setRole(role);

        String token = "token";

    }

    @Test
    void testCreateUser_Success() throws Exception {
        when(userRepository.existsByPhoneNumber(anyString())).thenReturn(false);
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals("123456789", createdUser.getPhoneNumber());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_PhoneNumberExists() {
        when(userRepository.existsByPhoneNumber(anyString())).thenReturn(true);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("Số điện thoại đã tồn tại", exception.getMessage());
    }

    @Test
    void testLogin_Success() throws Exception {
        when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenUtil.generateToken(any(User.class))).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA1NjQ4NzE0NDMiLCJ1c2VySWQiOjEyLCJzdWIiOiIwNTY0ODcxNDQzIiwiZXhwIjoxNzE4NzI2ODIwfQ.RPTJxQGXh2rrEUSzmIkT95tQGbhr-cgodLSUBNY2ewg");

        String token = userService.login("123456789", "password", 1L);

        assertNotNull(token);
        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA1NjQ4NzE0NDMiLCJ1c2VySWQiOjEyLCJzdWIiOiIwNTY0ODcxNDQzIiwiZXhwIjoxNzE4NzI2ODIwfQ.RPTJxQGXh2rrEUSzmIkT95tQGbhr-cgodLSUBNY2ewg", token);
    }
//    @Test
//    void testLogin_Success() throws Exception {
//        assertEquals(1,1);
//    }
    @Test
    void testLogin_WrongPassword() {
        when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            userService.login("123456789", "wrongpassword", 1L);
        });

        // Assert that the actual exception message is null
        assertNull(exception.getMessage());
    }



    @Test
    void testUpdateUser_Success() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFullName("Updated User");
        updateUserDTO.setPhoneNumber("987654321");

        User updatedUser = userService.updateUser(1L, updateUserDTO);

        assertNotNull(updatedUser);
        assertEquals("Updated User", updatedUser.getFullName());
        assertEquals("987654321", updatedUser.getPhoneNumber());
    }

    @Test
    void testGetUserDetailsFromToken_Success() throws Exception {
        when(jwtTokenUtil.isTokenExpired(anyString())).thenReturn(false);
        when(jwtTokenUtil.extractPhoneNumber(anyString())).thenReturn("123456789");
        when(userRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(user));

        User userFromToken = userService.getUserDetailsFromToken("token");

        assertNotNull(userFromToken);
        assertEquals("123456789", userFromToken.getPhoneNumber());
    }

    @Test
    void testGetUserDetailsFromToken_ExpiredToken() {
        when(jwtTokenUtil.isTokenExpired(anyString())).thenReturn(true);

        ExpiredTokenException exception = assertThrows(ExpiredTokenException.class, () -> {
            userService.getUserDetailsFromToken("expiredToken");
        });

        assertEquals("Token is expired", exception.getMessage());
    }
}
