package com.example.bookstore.service;



import java.util.List;

import com.example.bookstore.DTO.request.CategoryRequest;
import com.example.bookstore.DTO.respones.CategoryResponse;
import com.example.bookstore.entity.Category;

public interface CategoryService  {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long id , CategoryRequest request);

    void delete(Long id);

    CategoryResponse getById(Long id);

    Category getEntityById(Long id);

    List<CategoryResponse> ListAll();

    
} 
