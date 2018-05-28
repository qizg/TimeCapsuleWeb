package com.sinwn.capsule.service;

import com.sinwn.capsule.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> getUserList(String filterName, int page, int pageCount);

    int insertUser(UserEntity userEntity);

    int updateUser(UserEntity userEntity);

    int deleteUser(long id);
}
