package com.example.bookstore.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter 
@Table(name = "books" ,
            indexes = {
                @Index(name = "idx_books_title", columnList = "litle"),
                @Index(name="idx_books_author",columnList = "author")
            }
)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false,unique = true,length = 255)
    private String title ;

    @Column(nullable = false , length = 255)
    private String author ;

    @Column(nullable = false, unique = true,length= 20)
    private String isbn ;

    @Column(nullable = false,precision = 10 )
    private BigDecimal price ;

    @Column(nullable = false)
    private int stockQuantity ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categories;


    @Column(updatable = false, nullable = false, name = "created_at")
    private LocalDateTime createdAt ;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt ;

    @Version 
    private  long version;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt =  LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }



}
