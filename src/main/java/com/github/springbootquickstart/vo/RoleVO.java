package com.github.springbootquickstart.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVO extends BaseVO {
    private Long id;

    private String name;

    private String description;

    private Set<PermissionVO> permissions;
} 