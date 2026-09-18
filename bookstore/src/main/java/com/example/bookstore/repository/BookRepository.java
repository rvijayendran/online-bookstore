package com.example.bookstore.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
      Page<Book> findByCategory(Long categoryTD , Pageable pageable);
      Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
      Page<Book> findByAuthorContainingIgnoreCase(String author , Pageable pageable);

      Optional<Book> findByIsbn(String isbn);

      boolean existsByIsbn(String isbn);

      @Query("SELECT b FROM Book b WHERE b.stockQuantity > :minStock")
    Page<Book> findInStock(@Param("minStock") int minStock, Pageable pageable);

      @Query("SELECT b FROM Book b JOIN FETCH b.category WHERE b.id = :id")
    Optional<Book> findByIdWithCategory(@Param("id") Long id);

    /**
     * List all books cheaper than a given price.
     */
    @Query("SELECT b FROM Book b WHERE b.price < :maxPrice ORDER BY b.price ASC")
    List<Book> findCheaperThan(@Param("maxPrice") java.math.BigDecimal maxPrice);
}
