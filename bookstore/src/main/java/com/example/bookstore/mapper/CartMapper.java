package com.example.bookstore.mapper;


import com.example.bookstore.DTO.respones.CartResponse;

import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "items", source = "cartItems")
    @Mapping(target = "totalPrice", expression = "java(computeTotal(cart))")
    CartResponse toResponse(Cart cart);

    default BigDecimal computeTotal(Cart cart) {
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return cart.getCartItems().stream()
                .map(this::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

default BigDecimal lineTotal(CartItem item) {
    if (item == null
        || item.getBook() == null
        || item.getBook().getPrice() == null
        || item.getQuantity() == null
        || item.getQuantity() <= 0) {
        return BigDecimal.ZERO; // or throw — see note below
    }
    return item.getBook().getPrice()
            .multiply(BigDecimal.valueOf(item.getQuantity()))
            .setScale(2, RoundingMode.HALF_UP);
}
}