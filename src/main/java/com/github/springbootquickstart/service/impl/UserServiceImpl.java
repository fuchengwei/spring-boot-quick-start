package com.github.springbootquickstart.service.impl;

import com.github.springbootquickstart.entity.Role;
import com.github.springbootquickstart.entity.User;
import com.github.springbootquickstart.repository.RoleRepository;
import com.github.springbootquickstart.repository.UserRepository;
import com.github.springbootquickstart.service.UserService;
import lombok.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, @NonNull UserRepository> implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameWithRoles(String username) {
        return userRepository.findByUsernameWithRoles(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User existingUser = findById(user.getId()).orElseThrow(() -> new RuntimeException("用户不存在"));

        // 不更新密码
        user.setPassword(existingUser.getPassword());
        return save(user);
    }

    @Override
    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = findById(userId).orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        save(user);
    }

    @Override
    @Transactional
    public void assignRoles(Long userId, List<Long> roleIds) {
        User user = findById(userId).orElseThrow(() -> new RuntimeException("用户不存在"));

        List<Role> roles = roleRepository.findAllByIdWithPermissions(roleIds);
        user.getRoles().addAll(roles);
        save(user);
    }

    @Override
    @Transactional
    public void removeRoles(Long userId, List<Long> roleIds) {
        User user = findById(userId).orElseThrow(() -> new RuntimeException("用户不存在"));

        user.getRoles().removeIf(role -> roleIds.contains(role.getId()));
        save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsernameWithRoles(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                                                       .flatMap(role -> role.getPermissions().stream())
                                                       .map(permission -> new SimpleGrantedAuthority(permission.getCode()))
                                                       .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), true, true, true, authorities);
    }
} 