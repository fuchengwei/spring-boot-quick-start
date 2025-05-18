package com.github.springbootquickstart.controller;

import com.github.springbootquickstart.common.Result;
import com.github.springbootquickstart.converter.PermissionConverter;
import com.github.springbootquickstart.dto.PermissionDTO;
import com.github.springbootquickstart.entity.Permission;
import com.github.springbootquickstart.enums.PermissionType;
import com.github.springbootquickstart.service.PermissionService;
import com.github.springbootquickstart.vo.PermissionVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;
    private final PermissionConverter permissionConverter;

    @PostMapping
    @PreAuthorize("hasAuthority('permission:create')")
    public Result<PermissionVO> createPermission(@Valid @RequestBody PermissionDTO permissionDTO) {
        Permission permission = permissionConverter.toEntity(permissionDTO);
        Permission createdPermission = permissionService.save(permission);
        return Result.success(permissionConverter.toVO(createdPermission));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('permission:update')")
    public Result<PermissionVO> updatePermission(@PathVariable Long id,
                                                 @Valid @RequestBody PermissionDTO permissionDTO) {
        permissionDTO.setId(id);
        Permission permission = permissionConverter.toEntity(permissionDTO);
        Permission updatedPermission = permissionService.updatePermission(permission);
        return Result.success(permissionConverter.toVO(updatedPermission));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('permission:read')")
    public Result<PermissionVO> getPermission(@PathVariable Long id) {
        return permissionService.findById(id).map(permissionConverter::toVO).map(Result::success)
                                .orElse(Result.error("权限不存在"));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('permission:read')")
    public Result<List<PermissionVO>> getAllPermissions() {
        List<PermissionVO> permissionVOs = permissionService.findAll().stream().map(permissionConverter::toVO)
                                                            .collect(Collectors.toList());
        return Result.success(permissionVOs);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('permission:read')")
    public Result<Page<PermissionVO>> getPermissionsByPage(Pageable pageable) {
        Page<Permission> permissionPage = permissionService.findAll(pageable);
        Page<PermissionVO> permissionVOPage = permissionPage.map(permissionConverter::toVO);
        return Result.success(permissionVOPage);
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAuthority('permission:read')")
    public Result<List<PermissionVO>> getPermissionsByType(@PathVariable PermissionType type) {
        List<PermissionVO> permissionVOs = permissionService.findByType(type).stream().map(permissionConverter::toVO)
                                                            .collect(Collectors.toList());
        return Result.success(permissionVOs);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('permission:read')")
    public Result<List<PermissionVO>> getPermissionsByUserId(@PathVariable Long userId) {
        List<PermissionVO> permissionVOs = permissionService.findByUserId(userId).stream()
                                                            .map(permissionConverter::toVO)
                                                            .collect(Collectors.toList());
        return Result.success(permissionVOs);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('permission:delete')")
    public Result<Void> deletePermission(@PathVariable Long id) {
        permissionService.deleteById(id);
        return Result.success();
    }
} 