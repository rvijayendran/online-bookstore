package com.example.bookstore.mapper;

import com.example.bookstore.DTO.respones.OrderItemResponse;

import com.example.bookstore.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "subtotal", expression = "java(computeSubtotal(orderItem))")
    OrderItemResponse toResponse(OrderItem orderItem);

    List<OrderItemResponse> toResponseList(List<OrderItem> orderItems);

    default BigDecimal computeSubtotal(OrderItem item) {
        if (item.getPriceAtPurchase() == null || item.getQuantity() == null) {
            return BigDecimal.ZERO;
        }
        return item.getPriceAtPurchase()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
    }
}