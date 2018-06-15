package com.sinwn.capsule.service;

import com.sinwn.capsule.entity.PermissionEntity;
import com.sinwn.capsule.entity.RoleEntity;

import java.util.List;

public interface PermissionService {
    boolean addUserRole(int userId, int roleId);

    List<RoleEntity> getRolesByUserId(int userId);

    List<PermissionEntity> getPermissionsByRoleId(int roleId);
}
