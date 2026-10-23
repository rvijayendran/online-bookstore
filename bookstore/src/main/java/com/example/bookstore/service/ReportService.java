package com.example.bookstore.service;

public interface ReportService {
        void generateLowStockReport();

        String buildLowStockReport(int threshold);
    
} 
