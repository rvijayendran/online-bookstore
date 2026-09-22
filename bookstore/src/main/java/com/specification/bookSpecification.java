package com.specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.bookstore.entity.Book;

public final class bookSpecification {

    private bookSpecification(){

    }

    public  static  Specification<Book> hasTitle(String title){
        return (root,query,cb)-> {
            if (title == null || title.isBlank()) return null;
            return cb.like(cb.lower(root.get("title")),"%"+ title.toLowerCase() +"%");
        };
    }

    public  static  Specification<Book> hasAuthor(String author){
        return (root , query , cb)->{
            if(author == null || author.isBlank()) return  null;
            return cb.like(cb.lower(root.get("author")),
        "%"+author.toLowerCase()+"%");
        };
        }


        public static Specification<Book> hasCategory(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return null;
            return cb.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Book> hasPriceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) {
                return cb.between(root.get("price"), min, max);
            }
            if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), min);
            }
            return cb.lessThanOrEqualTo(root.get("price"), max);
        };
    }

    public static Specification<Book> isInStock() {
        return (root, query, cb) -> cb.greaterThan(root.get("stockQuantity"), 0);
    }

    /**
     * Combine multiple optional filters.
     * Only non-null specs are applied.
     */
    public static Specification<Book> build(String title,
                                            String author,
                                            Long categoryId,
                                            BigDecimal minPrice,
                                            BigDecimal maxPrice) {
        List<Specification<Book>> specs = new ArrayList<>();
        specs.add(hasTitle(title));
        specs.add(hasAuthor(author));
        specs.add(hasCategory(categoryId));
        specs.add(hasPriceBetween(minPrice, maxPrice));

        Specification<Book> result = null;
        for (Specification<Book> spec : specs) {
            if (spec == null) continue;
            result = (result == null) ? spec : result.and(spec);
        }
        return result; // may be null → findAll will return all
    }
    }
    

