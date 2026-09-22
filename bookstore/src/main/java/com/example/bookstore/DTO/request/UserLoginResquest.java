package com.example.bookstore.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginResquest(
        @NotBlank(message = "Username or email is required")
        String usernameOrEmail , 

        @NotBlank(message = "Password is required")
        String password

) {}
