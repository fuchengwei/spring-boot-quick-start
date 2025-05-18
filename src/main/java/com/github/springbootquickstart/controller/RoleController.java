package com.github.springbootquickstart.controller;

import com.github.springbootquickstart.common.Result;
import com.github.springbootquickstart.converter.RoleConverter;
import com.github.springbootquickstart.dto.RoleDTO;
import com.github.springbootquickstart.entity.Role;
import com.github.springbootquickstart.service.RoleService;
import com.github.springbootquickstart.vo.RoleVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final RoleConverter roleConverter;

    @PostMapping
    @PreAuthorize("hasAuthority('role:create')")
    public Result<RoleVO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        Role role = roleConverter.toEntity(roleDTO);
        Role createdRole = roleService.createRole(role);
        return Result.success(roleConverter.toVO(createdRole));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('role:update')")
    public Result<RoleVO> updateRole(@PathVariable Long id, @Valid @RequestBody RoleDTO roleDTO) {
        roleDTO.setId(id);
        Role role = roleConverter.toEntity(roleDTO);
        Role updatedRole = roleService.updateRole(role);
        return Result.success(roleConverter.toVO(updatedRole));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('role:read')")
    public Result<RoleVO> getRole(@PathVariable Long id) {
        return roleService.findByIdWithPermissions(id).map(roleConverter::toVO).map(Result::success)
                          .orElse(Result.error("角色不存在"));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('role:read')")
    public Result<List<RoleVO>> getAllRoles() {
        List<RoleVO> roleVOs = roleService.findAll().stream().map(roleConverter::toVO).collect(Collectors.toList());
        return Result.success(roleVOs);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('role:read')")
    public Result<Page<RoleVO>> getRolesByPage(Pageable pageable) {
        Page<Role> rolePage = roleService.findAll(pageable);
        Page<RoleVO> roleVOPage = rolePage.map(roleConverter::toVO);
        return Result.success(roleVOPage);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('role:delete')")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('role:assign-permission')")
    public Result<Void> assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        roleService.assignPermissions(id, permissionIds);
        return Result.success();
    }

    @DeleteMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('role:assign-permission')")
    public Result<Void> removePermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        roleService.removePermissions(id, permissionIds);
        return Result.success();
    }
} 