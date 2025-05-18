package com.github.springbootquickstart.service.impl;

import com.github.springbootquickstart.entity.Permission;
import com.github.springbootquickstart.enums.PermissionType;
import com.github.springbootquickstart.repository.PermissionRepository;
import com.github.springbootquickstart.service.PermissionService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long, @NonNull PermissionRepository> implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Optional<Permission> findByCode(String code) {
        return permissionRepository.findByCode(code);
    }

    @Override
    public boolean existsByCode(String code) {
        return permissionRepository.existsByCode(code);
    }

    @Override
    public List<Permission> findByType(PermissionType type) {
        return permissionRepository.findByType(type);
    }

    @Override
    public List<Permission> findByUserId(Long userId) {
        return permissionRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Permission createPermission(Permission permission) {
        if (existsByCode(permission.getCode())) {
            throw new RuntimeException("权限标识已存在");
        }
        return save(permission);
    }

    @Override
    @Transactional
    public Permission updatePermission(Permission permission) {
        Permission existingPermission = findById(permission.getId()).orElseThrow(() -> new RuntimeException("权限不存在"));

        if (!existingPermission.getCode().equals(permission.getCode()) && existsByCode(permission.getCode())) {
            throw new RuntimeException("权限标识已存在");
        }

        return save(permission);
    }
} 