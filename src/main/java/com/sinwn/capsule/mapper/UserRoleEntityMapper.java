package com.sinwn.capsule.mapper;

import com.sinwn.capsule.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleEntity record);

    int insertSelective(UserRoleEntity record);

    UserRoleEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRoleEntity record);

    int updateByPrimaryKey(UserRoleEntity record);
}