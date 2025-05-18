package com.github.springbootquickstart.converter;

import com.github.springbootquickstart.dto.UserDTO;
import com.github.springbootquickstart.entity.Role;
import com.github.springbootquickstart.entity.User;
import com.github.springbootquickstart.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getRoles() != null) {
            dto.setRoleIds(entity.getRoles().stream().map(Role::getId).collect(Collectors.toSet()));
        }
        return dto;
    }

    public UserVO toVO(User entity) {
        if (entity == null) {
            return null;
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(entity, vo);
        if (entity.getRoles() != null) {
            vo.setRoles(entity.getRoles().stream().map(role -> new RoleConverter().toVO(role))
                              .collect(Collectors.toSet()));
        }
        return vo;
    }
} 