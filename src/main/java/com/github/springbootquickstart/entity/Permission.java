package com.github.springbootquickstart.entity;

import com.github.springbootquickstart.enums.PermissionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sys_permission")
@EntityListeners(AuditingEntityListener.class)
public class Permission extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PermissionType type;

    private String icon;

    private String url;

    private String componentUrl;

    private String redirectUrl;

    private Integer sort;

    private Long parentId = (long) -1;

    private Boolean enabled = true;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles;
} 