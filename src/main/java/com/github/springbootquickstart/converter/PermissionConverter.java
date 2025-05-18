package com.github.springbootquickstart.converter;

import com.github.springbootquickstart.dto.PermissionDTO;
import com.github.springbootquickstart.entity.Permission;
import com.github.springbootquickstart.vo.PermissionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PermissionConverter {

    public Permission toEntity(PermissionDTO dto) {
        if (dto == null) {
            return null;
        }
        Permission entity = new Permission();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public PermissionDTO toDTO(Permission entity) {
        if (entity == null) {
            return null;
        }
        PermissionDTO dto = new PermissionDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public PermissionVO toVO(Permission entity) {
        if (entity == null) {
            return null;
        }
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
} 