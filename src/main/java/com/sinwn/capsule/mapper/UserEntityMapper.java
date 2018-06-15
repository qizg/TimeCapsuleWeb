package com.sinwn.capsule.mapper;

import com.github.pagehelper.Page;
import com.sinwn.capsule.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    Page<UserEntity> selectUsers(String filterName);

    UserEntity selectByUsername(String username);

    UserEntity selectByUserId(Integer userId);
}