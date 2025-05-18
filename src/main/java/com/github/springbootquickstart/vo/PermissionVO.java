package com.github.springbootquickstart.vo;

import com.github.springbootquickstart.enums.PermissionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVO extends BaseVO {
    private Long id;

    private String code;

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