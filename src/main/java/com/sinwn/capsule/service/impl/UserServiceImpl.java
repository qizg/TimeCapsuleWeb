package com.sinwn.capsule.service.impl;

import com.sinwn.capsule.entity.UserEntity;
import com.sinwn.capsule.mapper.UserEntityMapper;
import com.sinwn.capsule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityMapper userMapper;

    @Override
    public List<UserEntity> getUserList(String filterName, int page, int pageCount) {
        if (page <= 0) {
            page = 1;
        }
        if (pageCount <= 0) {
            pageCount = 10;
        }
        int start = (page - 1) * pageCount;

        return userMapper.selectUsers(filterName, start, pageCount);
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
}
