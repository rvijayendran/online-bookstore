package com.example.bookstore.service.impl;


import  java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.NotificationService;
import com.example.bookstore.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@RequiredArgsConstructor 
@Slf4j 
public class ReportServiceImpl implements ReportService {

    private final BookRepository bookRepository;
    private  final NotificationService notificationService;

    @Value("${bookstore.report.low-stock-threshold:5}")
    private int lowStockThreshold;

    @Value("${bookstore.report.admin-email:admin@bookstore.local}")
    private String adminEmail;

    @Scheduled(cron = "0 0 6 * * *")
    @Override 
    @Transactional(readOnly = true)
    public void generateLowStockReport(){
        String report = buildLowStockReport(lowStockThreshold);
        log.info("Daily low-stock report : \n{}", report);
        notificationService.sendLowStockAlert(adminEmail, report);
    }

    @Override 
    @Transactional(readOnly = true)
    public String buildLowStockReport(int threshold){
        List<Book> lowStock = bookRepository.findByStockQuantityLessThan(threshold);
        if(lowStock.isEmpty()){
            return "No low-stock books";

        }
        StringBuilder sb = new StringBuilder();
        sb.append("Low-stock books (threshold=").append(threshold).append("):\n");
        for(Book b : lowStock){
            sb.append(String.format("- %s (id=%d) : %d in stock %n", b.getTitle(), b.getId() , b.getStockQuantity()));
        }

        return sb.toString();
    }
    
}
