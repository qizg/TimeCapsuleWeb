package com.sinwn.capsule.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinwn.capsule.constant.Constant;
import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.entity.UserEntity;
import com.sinwn.capsule.mapper.UserEntityMapper;
import com.sinwn.capsule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserEntity findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
