package com.sinwn.capsule.mapper;

import com.sinwn.capsule.entity.PermissionEntity;
import com.sinwn.capsule.entity.RolePermissionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissionEntity record);

    int insertSelective(RolePermissionEntity record);

    RolePermissionEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermissionEntity record);

    int updateByPrimaryKey(RolePermissionEntity record);

    List<PermissionEntity> selectByRoleId(int roleId);
}