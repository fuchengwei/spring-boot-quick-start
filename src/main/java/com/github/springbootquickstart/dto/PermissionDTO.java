package com.github.springbootquickstart.dto;

import com.github.springbootquickstart.enums.PermissionType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PermissionDTO {
    private Long id;

    @NotBlank(message = "权限标识不能为空")
    private String code;

    @NotBlank(message = "权限名称不能为空")
    private String name;

    private String description;

    private PermissionType type;

    private String icon;

    private String url;

    private String componentUrl;

    private String redirectUrl;

    private Integer sort;

    private Long parentId;

    private Boolean enabled;
} 