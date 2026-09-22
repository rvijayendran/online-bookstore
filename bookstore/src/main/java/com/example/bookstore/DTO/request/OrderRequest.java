package com.example.bookstore.DTO.request;

import java.util.List;

import com.example.bookstore.entity.OrderItem;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record OrderRequest(
    @NotEmpty(message = "order must have at least one item ")
    @Valid 
    List<OrderItemRequest> items 
) {
    
}
