package com.example.bookstore.projection;

import java.math.BigDecimal;

public interface BookSummary {
    Long getId();
    String getTitle();
    String getAuthor();
    BigDecimal getPrice();
}
