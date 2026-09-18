package com.example.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.entity.Roles;

public interface RoleRepository extends  JpaRepository<Roles, Long>  {

    Optional<Roles> findByName(String  name);

    boolean existsByName(String name);

    
} 
