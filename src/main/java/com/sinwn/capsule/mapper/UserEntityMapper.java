package com.sinwn.capsule.mapper;

import com.github.pagehelper.Page;
import com.sinwn.capsule.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    Page<UserEntity> selectUsers(String filterName);

    UserEntity selectByUsername(String username);

    UserEntity selectByUserId(long userId);
}