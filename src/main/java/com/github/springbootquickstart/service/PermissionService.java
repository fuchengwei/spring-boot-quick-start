package com.github.springbootquickstart.service;

import com.github.springbootquickstart.entity.Permission;
import com.github.springbootquickstart.enums.PermissionType;

import java.util.List;
import java.util.Optional;

public interface PermissionService extends BaseService<Permission, Long> {
    Optional<Permission> findByCode(String code);

    boolean existsByCode(String code);

    List<Permission> findByType(PermissionType type);

    List<Permission> findByUserId(Long userId);

    Permission createPermission(Permission permission);

    Permission updatePermission(Permission permission);
} 