package com.example.bookstore.exception;

public class InsufficientStockException extends RuntimeException {

    private final String bookTitle;
    private final int available;
    private final int requested;

    public InsufficientStockException(String bookTitle, int available, int requested) {
        super(String.format("Insufficient stock for '%s': requested %d, available %d",
                bookTitle, requested, available));
        this.bookTitle = bookTitle;
        this.available = available;
        this.requested = requested;
    }

    public String getBookTitle() { return bookTitle; }
    public int getAvailable()    { return available; }
    public int getRequested()    { return requested; }
}