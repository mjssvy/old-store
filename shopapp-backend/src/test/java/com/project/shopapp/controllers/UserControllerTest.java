package com.project.shopapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.responses.user.LoginResponse;
import com.project.shopapp.responses.user.UserListResponse;
import com.project.shopapp.responses.user.UserResponse;
import com.project.shopapp.services.token.ITokenService;
import com.project.shopapp.services.user.IUserService;
import com.project.shopapp.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUserService userService;

    @Mock
    private ITokenService tokenService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllUser() throws Exception {
        // Prepare test data
        List<UserResponse> users = new ArrayList<>();
        users.add(new UserResponse());
        users.add(new UserResponse());

        Page<UserResponse> userPage = new PageImpl<>(users);

        when(userService.findAll(anyString(), any(PageRequest.class))).thenReturn(userPage);

        // Perform GET request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .param("keyword", "")
                .param("page", "0")
                .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Verify the response
        String responseBody = result.getResponse().getContentAsString();
        ResponseObject responseObject = new ObjectMapper().readValue(responseBody, ResponseObject.class);
        UserListResponse userListResponse = (UserListResponse) responseObject.getData();

        assert userListResponse != null;
        assert userListResponse.getUsers().size() == 2;
        assert userListResponse.getTotalPages() == 1;
    }

    @Test
    public void testCreateUser() throws Exception {
        // Prepare test data
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userService.createUser(any())).thenReturn(user);

        // Perform POST request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testuser\", \"password\": \"password\", \"retypePassword\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Verify the response
        String responseBody = result.getResponse().getContentAsString();
        ResponseObject responseObject = new ObjectMapper().readValue(responseBody, ResponseObject.class);
        UserResponse userResponse = (UserResponse) responseObject.getData();

        assert userResponse != null;
        assert userResponse.getId() == 1L;
        assert userResponse.getUsername().equals("testuser");
    }

    @Test
    public void testLogin() throws Exception {
        // Prepare test data
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userService.login(anyString(), anyString(), anyInt())).thenReturn("token");
        when(request.getHeader(eq("User-Agent"))).thenReturn("Mobile");

        // Perform POST request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phoneNumber\": \"123456789\", \"password\": \"password\", \"roleId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Verify the response
        String responseBody = result.getResponse().getContentAsString();
        ResponseObject responseObject = new ObjectMapper().readValue(responseBody, ResponseObject.class);
        LoginResponse loginResponse = (LoginResponse) responseObject.getData();

        assert loginResponse != null;
        assert loginResponse.getToken().equals("token");
        assert loginResponse.getUsername().equals("testuser");
    }
}