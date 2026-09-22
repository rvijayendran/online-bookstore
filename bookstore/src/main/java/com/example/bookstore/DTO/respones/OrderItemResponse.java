package com.example.bookstore.DTO.respones;

import java.math.BigDecimal;

public record OrderItemResponse (
    Long id ,
    Long bookId,
    String bookTitle ,
    Integer quantity ,
    BigDecimal priceAtPurchase , 
    BigDecimal subtotal
) {}
