package com.example.bookstore.DTO.respones;

public record AuthResponse(

    String token ,
    String tokenType ,  
    Long expiresIn , 
    String username
) {
    
}
