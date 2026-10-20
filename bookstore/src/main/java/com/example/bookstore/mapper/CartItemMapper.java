package com.example.bookstore.mapper;

import com.example.bookstore.dto.response.CartItemResponse;
import com.example.bookstore.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "pricePerUnit", source = "book.price")
    @Mapping(target = "subtotal", expression = "java(computeSubtotal(cartItem))")
    CartItemResponse toResponse(CartItem cartItem);

    List<CartItemResponse> toResponseList(List<CartItem> cartItems);

    default BigDecimal computeSubtotal(CartItem item) {
        if (item.getBook() == null || item.getQuantity() == null) return BigDecimal.ZERO;
        return item.getBook().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
    }
}