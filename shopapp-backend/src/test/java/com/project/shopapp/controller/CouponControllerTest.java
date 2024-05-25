package com.project.shopapp.controller;

import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.responses.coupon.CouponCalculationResponse;
import com.project.shopapp.services.coupon.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.project.shopapp.controllers.CouponController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CouponControllerTest {

    @InjectMocks
    private CouponController couponController;

    @Mock
    private CouponService couponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateCouponValue_Success() {
        // Arrange
        String couponCode = "DISCOUNT10";
        double totalAmount = 100.0;
        double finalAmount = 90.0; // Assuming the coupon gives a 10% discount

        when(couponService.calculateCouponValue(couponCode, totalAmount)).thenReturn(finalAmount);

        // Act
        ResponseEntity<ResponseObject> response = couponController.calculateCouponValue(couponCode, totalAmount);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Calculate coupon successfully", response.getBody().getMessage());
        assertEquals(HttpStatus.OK, response.getBody().getStatus());

        CouponCalculationResponse responseData = (CouponCalculationResponse) response.getBody().getData();
        assertNotNull(responseData);
        assertEquals(finalAmount, responseData.getResult());
    }
}
