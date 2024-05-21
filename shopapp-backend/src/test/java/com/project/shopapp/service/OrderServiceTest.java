package com.project.shopapp.service;


import com.project.shopapp.dtos.CartItemDTO;
import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.CouponRepository;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.orders.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class OrderServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateOrder() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(1L);
        orderDTO.setTotalMoney(100.0F);
        orderDTO.setCouponCode("TESTCODE");
        orderDTO.setCartItems(Collections.singletonList(new CartItemDTO(1L, 2)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(couponRepository.findByCode("TESTCODE")).thenReturn(Optional.of(new Coupon()));
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        when(modelMapper.map(orderDTO, Order.class)).thenReturn(new Order());

        // Act
        assertDoesNotThrow(() -> orderService.createOrder(orderDTO));

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderDetailRepository, times(1)).saveAll(anyList());
    }

    // You can add more test methods for other scenarios
}
