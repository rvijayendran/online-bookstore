package com.example.bookstore.service;

import com.example.bookstore.DTO.request.UserLoginResquest;
import com.example.bookstore.DTO.respones.AuthResponse;

public interface AuthService {

    AuthResponse login(UserLoginResquest resquest);
    
} 
