package com.sinwn.capsule.service.impl;

import com.sinwn.capsule.entity.UserRoleEntity;
import com.sinwn.capsule.mapper.UserRoleEntityMapper;
import com.sinwn.capsule.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final UserRoleEntityMapper roleMapper;

    @Autowired
    public PermissionServiceImpl(UserRoleEntityMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public boolean addUserRole(int userId, int roleId) {
        UserRoleEntity roleEntity = new UserRoleEntity();
        roleEntity.setUserId(userId);
        roleEntity.setRoleId(roleId);
        int count = roleMapper.insert(roleEntity);
        return count > 0;
    }
}
