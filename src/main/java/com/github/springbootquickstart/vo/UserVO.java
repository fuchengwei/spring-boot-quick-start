package com.github.springbootquickstart.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseVO {
    private Long id;

    private String username;

    private String email;

    private String phone;

    private Boolean enabled;

    private Set<RoleVO> roles;
}