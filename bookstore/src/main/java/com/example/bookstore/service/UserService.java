package com.example.bookstore.service;

import com.example.bookstore.DTO.request.UserResgistrationRequest;
import com.example.bookstore.DTO.respones.UserResponse;
import com.example.bookstore.entity.User;

public interface UserService {

    UserResponse register(UserResgistrationRequest request);

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    User getEntityById(Long id);

    User getEntityByUsername(String username);

    


}