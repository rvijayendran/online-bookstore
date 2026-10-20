package com.example.bookstore.service;

import com.example.bookstore.DTO.request.AddToCartRequest;
import com.example.bookstore.DTO.request.UpdateCartItemRequest;
import com.example.bookstore.DTO.respones.CartResponse;

public interface CartService {

    CartResponse getCartForUser(Long userId);

    CartResponse addItem(Long userId , AddToCartRequest request);

    CartResponse updateItemQuantity( Long userId , Long cartItemId , UpdateCartItemRequest request);

    CartResponse removeItem(Long userId , Long cartItemId);

    void clearCart(Long userId);

    Cart getEntityByUserId(Long userId);

    
} 
