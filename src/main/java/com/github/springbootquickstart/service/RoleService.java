package com.github.springbootquickstart.service;

import com.github.springbootquickstart.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService extends BaseService<Role, Long> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    Optional<Role> findByIdWithPermissions(Long id);

    List<Role> findAllByIdWithPermissions(List<Long> ids);

    Role createRole(Role role);

    Role updateRole(Role role);

    void assignPermissions(Long roleId, List<Long> permissionIds);

    void removePermissions(Long roleId, List<Long> permissionIds);
} 