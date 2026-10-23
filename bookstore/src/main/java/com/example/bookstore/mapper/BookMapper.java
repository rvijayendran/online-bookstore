package com.example.bookstore.mapper;

import com.example.bookstore.DTO.request.BookRequest;

import com.example.bookstore.DTO.respones.BookResponse;
import com.example.bookstore.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface BookMapper {

    @Mapping(target = "category", source = "category")
    BookResponse toResponse(Book book);

    List<BookResponse> toResponseList(List<Book> books);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book toEntity(BookRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(BookRequest request, @MappingTarget Book book);
}