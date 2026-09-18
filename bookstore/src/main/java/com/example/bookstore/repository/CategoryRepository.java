package com.example.bookstore.repository;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.Category;

@Repository 
public interface CategoryRepository  extends  JpaRepository <Category , Long> {
        Optional<Category> findByName(String name );

        boolean existsByName(String name);
    
} 
