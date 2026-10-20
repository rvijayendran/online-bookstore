package com.example.bookstore.service.impl;

import java.math.BigDecimal;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.DTO.request.BookRequest;
import com.example.bookstore.DTO.respones.BookResponse;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Category;
import com.example.bookstore.exception.DuplicateResourceException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.CategoryService;
import com.specification.bookSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@RequiredArgsConstructor 
@Slf4j 
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final BookMapper bookMapper;


    @Override 
    @Transactional 
    @CacheEvict(value = "books",allEntries = true)
    public BookResponse create(BookRequest request){
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new DuplicateResourceException("Book", "isbn", request.isbn());

            
        }
        Category category = categoryService.getEntityById(request.categoryId());

        Book book = bookMapper.toEntity(request);
        book.setCategory(category);


        Book saved = bookRepository.save(book);
        log.info("Created book: {} (id={})", saved.getTitle() , saved.getId());
        return bookMapper.toResponse(saved);
    }

    @Override 
    @Transactional 
    @CacheEvict(value = "books" , allEntries = true)
    public BookResponse update(Long id, BookRequest request){
        Book book = getEntityById(id);

        if (!book.getIsbn().equals(request.isbn())  && bookRepository.existsByIsbn(request.isbn())) {
            throw new DuplicateResourceException("Book", "isbn", request.isbn());
            
        }
        Category category = categoryService.getEntityById(request.categoryId());

        bookMapper.updateEntityFromRequest(request, book);
        book.setCategory(category);

        Book updated = bookRepository.save(book);
        log.info("Updated book id={}", id);
        return bookMapper.toResponse(updated);
        
    }

    @Override
    @Transactional 
    @CacheEvict(value = "books", allEntries = true)
    public void delete(Long id){
        Book book = getEntityById(id);
        bookRepository.delete(book);
        log.info("Deleted book id={}",id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "books", key =  "#id")
    public BookResponse getById(Long id){
        return  bookMapper.toResponse(getEntityById(id));

    }

    @Override 
    @Transactional(readOnly = true)
    public Book getEntityById(Long id){
        return bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book","id",id));

    }

    @Override 
    @Transactional(readOnly = true)
    public Page<BookResponse> search(String title, String author , Long categoryId,
                                        BigDecimal minPrice , BigDecimal maxPrice,
                                        Pageable pageable){
                        Specification<Book> spec = bookSpecification.build(title, author, categoryId, minPrice, maxPrice);
                        return bookRepository.findAll(spec, pageable).map(bookMapper::toResponse);
                                        }
    
    @Override 
    @Transactional(readOnly = true)
    public Page<BookResponse> listAll(Pageable pageable){
        return bookRepository.findAll(pageable).map(bookMapper::toResponse);
    }
    
}
