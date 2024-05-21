package com.project.shopapp.service;


import com.project.shopapp.models.Coupon;
import com.project.shopapp.models.CouponCondition;
import com.project.shopapp.repositories.CouponConditionRepository;
import com.project.shopapp.repositories.CouponRepository;
import com.project.shopapp.services.coupon.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponConditionRepository couponConditionRepository;

    @InjectMocks
    private CouponService couponService;

    private Coupon coupon;
    private CouponCondition minimumAmountCondition;
    private CouponCondition applicableDateCondition;

    @BeforeEach
    void setUp() {
        coupon = new Coupon();
        coupon.setId(1L);
        coupon.setCode("DISCOUNT10");
        coupon.setActive(true);

        minimumAmountCondition = new CouponCondition();
        minimumAmountCondition.setAttribute("minimum_amount");
        minimumAmountCondition.setOperator(">");
        minimumAmountCondition.setValue("100");
        minimumAmountCondition.setDiscountAmount(BigDecimal.valueOf(10.0));

        applicableDateCondition = new CouponCondition();
        applicableDateCondition.setAttribute("applicable_date");
        applicableDateCondition.setOperator("BETWEEN");
        applicableDateCondition.setValue(LocalDate.now().toString());
        applicableDateCondition.setDiscountAmount(BigDecimal.valueOf(5.0));
    }

    @Test
    void calculateCouponValue_Success() {
        when(couponRepository.findByCode("DISCOUNT10")).thenReturn(Optional.of(coupon));
        when(couponConditionRepository.findByCouponId(1L)).thenReturn(List.of(minimumAmountCondition, applicableDateCondition));

        double finalAmount = couponService.calculateCouponValue("DISCOUNT10", 200.0);

        assertEquals(160.0, finalAmount, 0.01);

        verify(couponRepository).findByCode("DISCOUNT10");
        verify(couponConditionRepository).findByCouponId(1L);
    }

    @Test
    void calculateCouponValue_CouponNotFound() {
        when(couponRepository.findByCode("DISCOUNT10")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            couponService.calculateCouponValue("DISCOUNT10", 200.0);
        });

        assertEquals("Coupon not found", exception.getMessage());
    }

    @Test
    void calculateCouponValue_CouponNotActive() {
        coupon.setActive(false);
        when(couponRepository.findByCode("DISCOUNT10")).thenReturn(Optional.of(coupon));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            couponService.calculateCouponValue("DISCOUNT10", 200.0);
        });

        assertEquals("Coupon is not active", exception.getMessage());
    }

    @Test
    void calculateDiscount_MinimumAmountCondition_Success() {
        List<CouponCondition> conditions = Collections.singletonList(minimumAmountCondition);
        double discount = couponService.calculateDiscount(coupon, 200.0);

        assertEquals(20.0, discount, 0.01);
    }

    @Test
    void calculateDiscount_ApplicableDateCondition_Success() {
        List<CouponCondition> conditions = Collections.singletonList(applicableDateCondition);
        when(couponConditionRepository.findByCouponId(1L)).thenReturn(conditions);

        double discount = couponService.calculateDiscount(coupon, 200.0);

        assertEquals(10.0, discount, 0.01);
    }
}

