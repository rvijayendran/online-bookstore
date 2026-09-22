package com.example.bookstore.DTO.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequest (
    @NotBlank(message = "Title is required")
    @Size(max =255)
    String title,

    @NotBlank(message = "Author is required")
    @Size(max = 255)
    String author ,

    @NotBlank(message = "ISBN is required")
    @Size(max = 20)
    String isbn,

    @NotBlank(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    BigDecimal price,

    @NotNull(message = "Stock Quantity is required ")
    @Min(value = 0,message = "Stock Quantity cannot be negative")
    Integer stockQuantity ,

    @NotNull(message = "Category is required ")
    Long categoryId


) {
    
}
