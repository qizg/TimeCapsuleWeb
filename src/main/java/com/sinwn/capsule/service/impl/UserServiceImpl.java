package com.sinwn.capsule.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinwn.capsule.constant.Constant;
import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.response.LoginResponse;
import com.sinwn.capsule.entity.UserEntity;
import com.sinwn.capsule.mapper.UserEntityMapper;
import com.sinwn.capsule.service.UserService;
import com.sinwn.capsule.utils.JWTUtil;
import com.sinwn.capsule.utils.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityMapper userMapper;

    @Autowired
    public UserServiceImpl(UserEntityMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ResponseBean<ResultListData<UserEntity>> getUserList(
            String filterName, String strPageNo, String strPageCount) {
        int pageNo = 1, pageCount = 10;

        try {
            if (strPageNo != null) {
                pageNo = Integer.valueOf(strPageNo);
                if (pageNo <= 0) {
                    pageNo = 1;
                }
            }
            if (strPageCount != null) {
                pageCount = Integer.valueOf(strPageCount);
                if (pageCount <= 0) {
                    pageCount = 10;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageHelper.startPage(pageNo, pageCount);
        Page<UserEntity> page = userMapper.selectUsers(filterName);

        ResponseBean<ResultListData<UserEntity>> resultData
                = new ResponseBean<>(Constant.STATUS_SUCCESS, StrConstant.SUCCESS);
        resultData.setData(new ResultListData<>(page));

        return resultData;
    }

    @Override
    public int insertUser(UserEntity userEntity) {

        return userMapper.insert(userEntity);
    }

    @Override
    public int updateUser(UserEntity userEntity) {
        return userMapper.updateByPrimaryKeySelective(userEntity);
    }

    @Override
    public int deleteUser(long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public LoginResponse loginByUserName(String userName, String password) {

        UserEntity entity = userMapper.selectByUsername(userName);

        if (entity == null) {
            return null;
        } else {
            String secret = entity.getCreateTime().getTime() + StrConstant.PASSWORD_SALT;
            String handlePassword = TransUtil.encrypt(password, secret);
            if (!entity.getPassword().equals(handlePassword)) {
                return null;
            }

            LoginResponse response = new LoginResponse();
            response.setUserId(entity.getId());
            response.setEmail(entity.getEmail());
            response.setPhone(entity.getPhone());
            response.setNickName(entity.getNickName());
            String superToken = JWTUtil.sign(entity.getId(), UUID.randomUUID().toString(), secret);
            response.setSuperToken(superToken);
            return response;
        }

    }

    @Override
    public UserEntity findByUserId(long userId) {
        return userMapper.selectByUserId(userId);
    }
}
