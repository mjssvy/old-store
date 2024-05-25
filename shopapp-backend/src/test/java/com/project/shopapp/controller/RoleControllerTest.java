package com.project.shopapp.controller;

import com.project.shopapp.models.Role;
import com.project.shopapp.controllers.RoleController;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.services.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles_Success() {
        // Arrange
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("ROLE_ADMIN");

        List<Role> roles = Arrays.asList(role1, role2);
        when(roleService.getAllRoles()).thenReturn(roles);

        // Act
        ResponseEntity<ResponseObject> response = roleController.getAllRoles();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Get roles successfully", response.getBody().getMessage());
        assertEquals(roles, response.getBody().getData());
    }
}
