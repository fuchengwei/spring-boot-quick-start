package com.github.springbootquickstart.repository;

import com.github.springbootquickstart.entity.Permission;
import com.github.springbootquickstart.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByCode(String code);

    boolean existsByCode(String code);

    List<Permission> findByType(PermissionType type);

    @Query("SELECT DISTINCT p FROM Permission p " + "INNER JOIN p.roles r " + "INNER JOIN r.users u " + "WHERE u.id =" +
            " :userId")
    List<Permission> findByUserId(@Param("userId") Long userId);
} 