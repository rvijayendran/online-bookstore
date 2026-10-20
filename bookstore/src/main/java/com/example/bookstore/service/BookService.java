package com.example.bookstore.service;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;

import com.example.bookstore.DTO.request.BookRequest;
import com.example.bookstore.DTO.respones.BookResponse;

public interface BookService {
        BookResponse create(BookRequest request);

        BookResponse update(Long id , BookRequest request);

        void delete(Long id);

        BookResponse getById(Long id);

        Book getEntityById(Long id);

        Page<BookResponse> search(String title , String author , Long categoryId , 
                                    BigDecimal minPrice, BigDecimal maxPrice, 
                                    Pageable pageable);

        
        Page<BookResponse> listAll(Pageable pageable);
    
} 
