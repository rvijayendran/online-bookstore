package com.example.bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.OrderStatus;

@Repository 
public  interface OrderRepository extends JpaRepository<Order , Long> {

    Page<Order> findByUserId(Long userId , Pageable pageable);

    Page<Order> findByUserIdAndStatus(Long userId , OrderStatus status , Pageable pageable);

    Page<Order> findByStatus(OrderStatus status , Pageable pageable);


    Long countByUserId(Long userId);


    
}