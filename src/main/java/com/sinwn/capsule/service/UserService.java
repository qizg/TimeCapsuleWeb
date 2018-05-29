package com.sinwn.capsule.service;

import com.sinwn.capsule.domain.ResultData;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.entity.UserEntity;

public interface UserService {

    ResultData<ResultListData<UserEntity>> getUserList(String filterName, String pageNo, String pageCount);

    int insertUser(UserEntity userEntity);

    int updateUser(UserEntity userEntity);

    int deleteUser(long id);
}
