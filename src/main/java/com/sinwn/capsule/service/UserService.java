package com.sinwn.capsule.service;

import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.entity.UserEntity;

public interface UserService {

    ResponseBean<ResultListData<UserEntity>> getUserList(String filterName, String pageNo, String pageCount);

    int insertUser(UserEntity userEntity);

    int updateUser(UserEntity userEntity);

    int deleteUser(long id);

    UserEntity findByUsername(String username);

    UserEntity findByUserId(long userId);
}
