package com.example.bookstore.DTO.respones;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookResponse (
    Long id,
    String title,
    String author ,
    String isbn,
    BigDecimal price ,
    Integer stockQuantity ,
    CartResponse category,
    LocalDateTime createdAt,
    LocalDateTime updateAt 
) {
    
}
