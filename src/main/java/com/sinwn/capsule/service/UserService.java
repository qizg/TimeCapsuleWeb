package com.sinwn.capsule.service;

import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.request.SignUpRequest;
import com.sinwn.capsule.domain.response.LoginResponse;
import com.sinwn.capsule.entity.UserEntity;

public interface UserService {

    ResponseBean<ResultListData<UserEntity>> getUserList(String filterName, String pageNo, String pageCount);

    ResponseBean insertUser(SignUpRequest request);

    int updateUser(UserEntity userEntity);

    int deleteUser(int id);

    LoginResponse loginByUserName(String userName, String password);

    UserEntity findByUserId(int userId);
}
