package com.example.bookstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bookstore.DTO.request.OrderRequest;
import com.example.bookstore.DTO.respones.OrderResponse;
import com.example.bookstore.entity.Order;

public interface OrderService {
    OrderResponse placeOrder(Long userId , OrderRequest request);

    Page<OrderResponse> getOrderHistory(Long userId , Pageable pageable);

    OrderResponse getOrderById(Long userId, Long orderId);

    Order getEntityById(Long orderId);
    
} 
