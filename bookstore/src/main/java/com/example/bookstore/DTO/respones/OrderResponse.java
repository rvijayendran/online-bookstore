package com.example.bookstore.DTO.respones;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    Long id,
    Long userId,
    String status ,
    BigDecimal totalAmount ,
    List<OrderItemResponse> items,
    LocalDateTime createdAt 
) {
    
}
