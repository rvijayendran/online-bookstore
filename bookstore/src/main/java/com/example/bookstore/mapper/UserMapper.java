package com.example.bookstore.mapper;

import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import com.example.bookstore.entity.Roles;
import com.example.bookstore.entity.User;

@Mapper(componentModel =  "spring")
public interface  UserMapper {

    @Mapper(target = "roles", source = "roles")
    UserResponse toResponse(User user);

    default Set<String> mapRoles(Set<Roles> roles){
        if (roles == null) return Set.of();
        return roles.stream()
        .map(Roles::getName)
        .collect(Collectors.toSet());
        
    }
    
}
