package com.example.bookstore.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.DTO.request.CategoryRequest;
import com.example.bookstore.DTO.respones.CategoryResponse;
import com.example.bookstore.entity.Category;
import com.example.bookstore.exception.DuplicateResourceException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.mapper.CategoryMapper;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override 
    @Transactional 
    @CacheEvict(value = "books", allEntries = true) 
    public CategoryResponse create(CategoryRequest request){
        if(categoryRepository.existsByName(request.name())){
            throw new  DuplicateResourceException("Category", "name", request.name());

        }
        Category saved = categoryRepository.save(categoryMapper.toEntity(request));
        return categoryMapper.toRespose(saved);
    }

    @Override 
    @Transactional 
    @CacheEvict(value = "books", allEntries = true)
    public  void delete(Long id){
        Category category = getEntityById(id);
        categoryRepository.delete(category);
    }


    @Override 
    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id){
        return  categoryMapper.toRespose(getEntityById(id));

    }

    @Override 
    @Transactional(readOnly = true)
    public Category getEntityById(Long id){
        return categoryRepository.findAllById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
    }

    @Override 
    @Transactional(readOnly = true)
    public List<CategoryResponse> listAll(){
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }


    
}
