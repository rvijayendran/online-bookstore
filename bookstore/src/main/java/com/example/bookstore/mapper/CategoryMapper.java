package com.example.bookstore.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import  com.example.bookstore.entity.Category;
import com.example.bookstore.DTO.request.CategoryRequest;
import com.example.bookstore.DTO.respones.CategoryResponse;

@Mapper(componentModel = "spring")
public interface  CategoryMapper {
    CategoryResponse toRespose(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

    Category toEntity(CategoryRequest request);

    void updateEntityFromRequest(CategoryRequest request , @MappingTarget Category category);
}
