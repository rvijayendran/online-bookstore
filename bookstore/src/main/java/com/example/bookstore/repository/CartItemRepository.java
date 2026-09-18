package com.example.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bookstore.entity.CartItem;

public interface CartItemRepository extends JpaRepository <CartItem , Long >  {

    List<CartItem> findByCartId(Long cartID);

    Optional<CartItem> findByCartIdAndBookId(Long cartId, Long bookId);

    @Modifying 
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") Long CartId);

    
} 
