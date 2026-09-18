package com.example.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.Cart;


@Repository 
public  interface CartRepository extends JpaRepository <Cart ,Long> {

    Optional<Cart> findByUserId(Long userId);


    boolean existsByUserId(Long userId);

    
} 