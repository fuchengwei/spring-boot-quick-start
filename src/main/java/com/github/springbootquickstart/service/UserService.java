package com.github.springbootquickstart.service;

import com.github.springbootquickstart.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User, Long>, UserDetailsService {
    Optional<User> findByUsername(String username);
    
    Optional<User> findByUsernameWithRoles(String username);
    
    boolean existsByUsername(String username);
    
    User createUser(User user);
    
    User updateUser(User user);
    
    void changePassword(Long userId, String oldPassword, String newPassword);
    
    void assignRoles(Long userId, List<Long> roleIds);
    
    void removeRoles(Long userId, List<Long> roleIds);
} 