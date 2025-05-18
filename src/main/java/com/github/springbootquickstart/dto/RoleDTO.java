package com.github.springbootquickstart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class RoleDTO {
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    @NotBlank(message = "角色编码不能为空")
    private String code;

    private String description;

    private Boolean enabled;

    private Set<Long> permissionIds;
} 