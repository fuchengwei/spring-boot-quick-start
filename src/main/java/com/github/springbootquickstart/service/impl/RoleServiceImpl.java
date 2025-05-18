package com.github.springbootquickstart.service.impl;

import com.github.springbootquickstart.entity.Permission;
import com.github.springbootquickstart.entity.Role;
import com.github.springbootquickstart.repository.PermissionRepository;
import com.github.springbootquickstart.repository.RoleRepository;
import com.github.springbootquickstart.service.RoleService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long, @NonNull RoleRepository> implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public Optional<Role> findByIdWithPermissions(Long id) {
        return roleRepository.findByIdWithPermissions(id);
    }

    @Override
    public List<Role> findAllByIdWithPermissions(List<Long> ids) {
        return roleRepository.findAllByIdWithPermissions(ids);
    }

    @Override
    @Transactional
    public Role createRole(Role role) {
        if (existsByName(role.getName())) {
            throw new RuntimeException("角色名称已存在");
        }
        return save(role);
    }

    @Override
    @Transactional
    public Role updateRole(Role role) {
        Role existingRole = findById(role.getId()).orElseThrow(() -> new RuntimeException("角色不存在"));

        if (!existingRole.getName().equals(role.getName()) && existsByName(role.getName())) {
            throw new RuntimeException("角色名称已存在");
        }

        return save(role);
    }

    @Override
    @Transactional
    public void assignPermissions(Long roleId, List<Long> permissionIds) {
        Role role = findById(roleId).orElseThrow(() -> new RuntimeException("角色不存在"));

        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        role.getPermissions().addAll(permissions);
        save(role);
    }

    @Override
    @Transactional
    public void removePermissions(Long roleId, List<Long> permissionIds) {
        Role role = findById(roleId).orElseThrow(() -> new RuntimeException("角色不存在"));

        role.getPermissions().removeIf(permission -> permissionIds.contains(permission.getId()));
        save(role);
    }
} 