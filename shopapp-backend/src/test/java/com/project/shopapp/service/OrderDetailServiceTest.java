package com.project.shopapp.service;


import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.orderdetails.OrderDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderDetailService orderDetailService;

    private OrderDetailDTO orderDetailDTO;
    private Order order;
    private Product product;
    private OrderDetail orderDetail;

    @BeforeEach
    void setUp() {
        orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderId(1L);
        orderDetailDTO.setProductId(1L);
        orderDetailDTO.setNumberOfProducts(5);
        orderDetailDTO.setPrice(100.0F);
        orderDetailDTO.setTotalMoney(500.0F);
        orderDetailDTO.setColor("Red");

        order = new Order();
        order.setId(1L);

        product = new Product();
        product.setId(1L);

        orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        orderDetail.setId(1L);
    }

    @Test
    void createOrderDetail_Success() throws Exception {
        when(orderRepository.findById(orderDetailDTO.getOrderId())).thenReturn(Optional.of(order));
        when(productRepository.findById(orderDetailDTO.getProductId())).thenReturn(Optional.of(product));
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(orderDetail);

        OrderDetail createdOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);

        assertNotNull(createdOrderDetail);
        assertEquals(orderDetailDTO.getNumberOfProducts(), createdOrderDetail.getNumberOfProducts());
        assertEquals(orderDetailDTO.getPrice(), createdOrderDetail.getPrice());
        assertEquals(orderDetailDTO.getTotalMoney(), createdOrderDetail.getTotalMoney());
        assertEquals(orderDetailDTO.getColor(), createdOrderDetail.getColor());

        verify(orderRepository).findById(orderDetailDTO.getOrderId());
        verify(productRepository).findById(orderDetailDTO.getProductId());
        verify(orderDetailRepository).save(any(OrderDetail.class));
    }

    @Test
    void createOrderDetail_OrderNotFound() {
        when(orderRepository.findById(orderDetailDTO.getOrderId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            orderDetailService.createOrderDetail(orderDetailDTO);
        });

        assertEquals("Cannot find Order with id : " + orderDetailDTO.getOrderId(), exception.getMessage());
    }

    @Test
    void createOrderDetail_ProductNotFound() {
        when(orderRepository.findById(orderDetailDTO.getOrderId())).thenReturn(Optional.of(order));
        when(productRepository.findById(orderDetailDTO.getProductId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            orderDetailService.createOrderDetail(orderDetailDTO);
        });

        assertEquals("Cannot find product with id: " + orderDetailDTO.getProductId(), exception.getMessage());
    }

    @Test
    void getOrderDetail_Success() throws DataNotFoundException {
        when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(orderDetail));

        OrderDetail foundOrderDetail = orderDetailService.getOrderDetail(1L);

        assertNotNull(foundOrderDetail);
        assertEquals(1L, foundOrderDetail.getId());
    }

    @Test
    void getOrderDetail_NotFound() {
        when(orderDetailRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            orderDetailService.getOrderDetail(1L);
        });

        assertEquals("Cannot find OrderDetail with id: 1", exception.getMessage());
    }

    @Test
    void updateOrderDetail_Success() throws DataNotFoundException {
        when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(orderDetail));
        when(orderRepository.findById(orderDetailDTO.getOrderId())).thenReturn(Optional.of(order));
        when(productRepository.findById(orderDetailDTO.getProductId())).thenReturn(Optional.of(product));
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(orderDetail);

        OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(1L, orderDetailDTO);

        assertNotNull(updatedOrderDetail);
        assertEquals(orderDetailDTO.getNumberOfProducts(), updatedOrderDetail.getNumberOfProducts());
        assertEquals(orderDetailDTO.getPrice(), updatedOrderDetail.getPrice());
        assertEquals(orderDetailDTO.getTotalMoney(), updatedOrderDetail.getTotalMoney());
        assertEquals(orderDetailDTO.getColor(), updatedOrderDetail.getColor());
    }

    @Test
    void updateOrderDetail_NotFound() {
        when(orderDetailRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            orderDetailService.updateOrderDetail(1L, orderDetailDTO);
        });

        assertEquals("Cannot find order detail with id: 1", exception.getMessage());
    }

    @Test
    void deleteById_Success() {
        doNothing().when(orderDetailRepository).deleteById(1L);

        orderDetailService.deleteById(1L);

        verify(orderDetailRepository).deleteById(1L);
    }

    @Test
    void findByOrderId_Success() {
        when(orderDetailRepository.findByOrderId(1L)).thenReturn(Collections.singletonList(orderDetail));

        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(1L);

        assertNotNull(orderDetails);
        assertFalse(orderDetails.isEmpty());
        assertEquals(1, orderDetails.size());
        assertEquals(orderDetail, orderDetails.get(0));
    }
}
