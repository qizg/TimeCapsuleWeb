package com.sinwn.capsule.mapper;

import com.github.pagehelper.Page;
import com.sinwn.capsule.config.ConfigMapper;
import com.sinwn.capsule.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityMapper extends ConfigMapper<UserEntity> {

    Page<UserEntity> selectUsers(String filterName);

    UserEntity selectByUsername(String username);
}