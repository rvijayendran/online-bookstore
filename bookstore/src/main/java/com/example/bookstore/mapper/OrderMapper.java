package com.example.bookstore.mapper;

import  com.example.bookstore.DTO.respones.OrderResponse;
import com.example.bookstore.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    @Mapping(target = "items", source = "orderItems")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);
}