package com.github.springbootquickstart.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserVO {
    private Long id;

    private String username;

    private String email;

    private String phone;

    private Boolean enabled;

    private Set<RoleVO> roles;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}