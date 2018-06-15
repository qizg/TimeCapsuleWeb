package com.sinwn.capsule.service.impl;

import com.sinwn.capsule.entity.PermissionEntity;
import com.sinwn.capsule.entity.RoleEntity;
import com.sinwn.capsule.entity.UserRoleEntity;
import com.sinwn.capsule.mapper.RolePermissionEntityMapper;
import com.sinwn.capsule.mapper.UserRoleEntityMapper;
import com.sinwn.capsule.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final UserRoleEntityMapper roleMapper;

    private final RolePermissionEntityMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(UserRoleEntityMapper roleMapper, RolePermissionEntityMapper permissionMapper) {
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public boolean addUserRole(int userId, int roleId) {
        UserRoleEntity roleEntity = new UserRoleEntity();
        roleEntity.setUserId(userId);
        roleEntity.setRoleId(roleId);
        int count = roleMapper.insert(roleEntity);
        return count > 0;
    }

    @Override
    public List<RoleEntity> getRolesByUserId(int userId) {

        return roleMapper.selectByUserId(userId);
    }

    @Override
    public List<PermissionEntity> getPermissionsByRoleId(int roleId) {
        return permissionMapper.selectByRoleId(roleId);
    }
}
