package com.github.springbootquickstart.converter;

import com.github.springbootquickstart.dto.RoleDTO;
import com.github.springbootquickstart.entity.Permission;
import com.github.springbootquickstart.entity.Role;
import com.github.springbootquickstart.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleConverter {

    public Role toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }
        Role entity = new Role();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public RoleDTO toDTO(Role entity) {
        if (entity == null) {
            return null;
        }
        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getPermissions() != null) {
            dto.setPermissionIds(entity.getPermissions().stream().map(Permission::getId).collect(Collectors.toSet()));
        }
        return dto;
    }

    public RoleVO toVO(Role entity) {
        if (entity == null) {
            return null;
        }
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(entity, vo);
        if (entity.getPermissions() != null) {
            vo.setPermissions(entity.getPermissions().stream()
                                    .map(permission -> new PermissionConverter().toVO(permission))
                                    .collect(Collectors.toSet()));
        }
        return vo;
    }
} 