package com.example.bookstore.DTO.respones;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse (

    Long id,
    String username ,
    String email , 
    Set<String> roles,
    LocalDateTime createdAt
) {
    
}
