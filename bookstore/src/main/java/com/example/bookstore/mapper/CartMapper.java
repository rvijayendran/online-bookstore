package com.example.bookstore.mapper;

import com.example.bookstore.dto.response.CartResponse;
import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
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
        if (item.getBook() == null || item.getQuantity() == null) return BigDecimal.ZERO;
        return item.getBook().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
    }
}