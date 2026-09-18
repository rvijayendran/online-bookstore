package com.example.bookstore.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Column(nullable = false, unique = true,length = 100)
    private String name;

    @Column (length=500)
    private String description;

    @OneToMany(mappedBy = "category" , cascade = { CascadeType.PERSIST , CascadeType.MERGE })
    private List<Book> books = new ArrayList<>();

    public  void addBook(Book book){
        books.add(book);
        book.setCategory(this);
    }

    public  void removeBook(Book book){
        books.remove(book);
        book.setCategory(null);
    }
    
}
