package com.example.bookstore.DTO.respones;

import java.math.BigDecimal;

public record CartItemResponse(
    Long id,
    Long bookId,
    String bookTitle,
    BigDecimal pricePerUnit ,
    Integer quantity,
    BigDecimal subtotal
) {}
