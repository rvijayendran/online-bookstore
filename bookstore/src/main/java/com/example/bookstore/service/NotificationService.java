package com.example.bookstore.service;

import java.math.BigDecimal;

public interface NotificationService {
        void sendOrderConfirmation(String toEmail , long orderId , BigDecimal total);

        void sendLowStockAlert(String toEmail , String reportBody);
    
} 