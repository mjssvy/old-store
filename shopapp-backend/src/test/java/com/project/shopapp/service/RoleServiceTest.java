package com.project.shopapp.service;

import com.project.shopapp.models.Role;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.services.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        // Arrange
        List<Role> mockRoles = new ArrayList<>();
        mockRoles.add(new Role(1L, "Admin"));
        mockRoles.add(new Role(2L, "User"));

        when(roleRepository.findAll()).thenReturn(mockRoles);

        // Act
        List<Role> result = roleService.getAllRoles();

        // Assert
        assertEquals(mockRoles.size(), result.size());
        for (int i = 0; i < mockRoles.size(); i++) {
            assertEquals(mockRoles.get(i).getId(), result.get(i).getId());
            assertEquals(mockRoles.get(i).getName(), result.get(i).getName());
        }
    }
}