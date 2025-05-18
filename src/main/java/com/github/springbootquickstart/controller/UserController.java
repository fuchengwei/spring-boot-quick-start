package com.github.springbootquickstart.controller;

import com.github.springbootquickstart.common.Result;
import com.github.springbootquickstart.converter.UserConverter;
import com.github.springbootquickstart.dto.UserDTO;
import com.github.springbootquickstart.entity.User;
import com.github.springbootquickstart.service.UserService;
import com.github.springbootquickstart.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    public Result<UserVO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userConverter.toEntity(userDTO);
        User createdUser = userService.createUser(user);
        return Result.success(userConverter.toVO(createdUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public Result<UserVO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        User user = userConverter.toEntity(userDTO);
        User updatedUser = userService.updateUser(user);
        return Result.success(userConverter.toVO(updatedUser));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public Result<UserVO> getUser(@PathVariable Long id) {
        return userService.findById(id).map(userConverter::toVO).map(Result::success)
                          .orElse(Result.error("用户不存在"));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public Result<List<UserVO>> getAllUsers() {
        List<UserVO> userVOs = userService.findAll().stream().map(userConverter::toVO).collect(Collectors.toList());
        return Result.success(userVOs);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('user:read')")
    public Result<Page<UserVO>> getUsersByPage(Pageable pageable) {
        Page<User> userPage = userService.findAll(pageable);
        Page<UserVO> userVOPage = userPage.map(userConverter::toVO);
        return Result.success(userVOPage);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/{id}/change-password")
    @PreAuthorize("hasAuthority('user:update')")
    public Result<Void> changePassword(@PathVariable Long id, @RequestParam String oldPassword,
                                       @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return Result.success();
    }

    @PostMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('user:assign-role')")
    public Result<Void> assignRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        userService.assignRoles(id, roleIds);
        return Result.success();
    }

    @DeleteMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('user:assign-role')")
    public Result<Void> removeRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        userService.removeRoles(id, roleIds);
        return Result.success();
    }
} 