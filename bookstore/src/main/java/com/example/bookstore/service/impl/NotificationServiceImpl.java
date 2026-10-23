package com.example.bookstore.service.impl;

import java.math.BigDecimal;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.bookstore.service.NotificationService;

import lombok.extern.slf4j.Slf4j;
@Service 
@Slf4j 
public class NotificationServiceImpl implements NotificationService {

    @Async("taskExecutor")
    @Override 
    public void sendOrderConfirmation(String toEmail , long orderId , BigDecimal total){
        log.info("[ASYNC] Sending order confirmation to {} for order {} (total ={})", toEmail, orderId , total);

        try{
            Thread.sleep(500);

        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        log.info("[ASYNC] Order confirmation sent to {}", toEmail);

    }

    @Async("taskExecutor")
    @Override
    public void sendLowStockAlert(String toEmail , String reportBody){
        log.warn("[ASYNC] low stock alert to {} : \n{}", toEmail , reportBody);
    }
    
}
