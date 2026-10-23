package com.example.bookstore.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.DTO.request.UserResgistrationRequest;
import com.example.bookstore.DTO.respones.UserResponse;

import com.example.bookstore.entity.Roles;
import com.example.bookstore.entity.User;
import com.example.bookstore.exception.DuplicateResourceException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.mapper.UserMapper;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@RequiredArgsConstructor 
@Slf4j 
public class UserServiceImpl implements  UserService{

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override 
    @Transactional 
    public UserResponse register(UserResgistrationRequest request){
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("User","email",request.email());
        }

        Roles userRole = roleRepository.findByName(DEFAULT_ROLE)
        .orElseThrow(()-> new ResourceNotFoundException("Role","name",DEFAULT_ROLE));

        Set<Roles> roles = new HashSet<>();
        roles.add(userRole);

        User user = User.builder()
                    .username(request.username())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .roles(roles)
                    .build();
        User saved = userRepository.save(user);
        log.info("Registered new user: {}", saved.getUsername());

        return  userMapper.toResponse(saved);
    }

    
    @Override
    @Transactional(readOnly = true)
    public UserResponse findByUsername(String username){
        return userMapper.toResponse(getEntityByUsername(username));
    }

    @Override 
    @Transactional(readOnly = true)
    public UserResponse findById(Long id){
        return userMapper.toResponse(getEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public User getEntityById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override 
    @Transactional(readOnly = true)
    public User getEntityByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User","username",username));
    }
    
}
