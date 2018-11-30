package com.sinwn.capsule.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinwn.capsule.constant.Constant;
import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.request.SignUpRequest;
import com.sinwn.capsule.domain.response.LoginResponse;
import com.sinwn.capsule.entity.UserEntity;
import com.sinwn.capsule.mapper.UserEntityMapper;
import com.sinwn.capsule.service.PermissionService;
import com.sinwn.capsule.service.UserService;
import com.sinwn.capsule.utils.JWTUtil;
import com.sinwn.capsule.utils.NumCheckUtil;
import com.sinwn.capsule.utils.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityMapper userMapper;

    private final PermissionService permissionService;

    @Autowired
    public UserServiceImpl(UserEntityMapper userMapper, PermissionService permissionService) {
        this.userMapper = userMapper;
        this.permissionService = permissionService;
    }

    @Override
    public ResponseBean<ResultListData<UserEntity>> getUserList(
            String filterName, String strPageNo, String strPageCount) {

        PageHelper.startPage(NumCheckUtil.pageNo(strPageNo), NumCheckUtil.pageCount(strPageCount));
        Page<UserEntity> page = userMapper.selectUsers(filterName);

        ResponseBean<ResultListData<UserEntity>> resultData
                = new ResponseBean<>(Constant.STATUS_SUCCESS, StrConstant.SUCCESS);
        resultData.setData(new ResultListData<>(page));

        return resultData;
    }

    @Override
    public ResponseBean insertUser(SignUpRequest request) {

        String phone = request.getPhone();
        String email = request.getEmail();
        String password = request.getPassword();

        String userName;
        if (phone != null && phone.trim().length() > 0) {
            userName = phone;
        } else if (email != null && email.trim().length() > 0) {
            userName = email;
        } else {
            return new ResponseBean(Constant.REQUEST_ERROR, StrConstant.REQ_ERROR);
        }

        UserEntity entity = userMapper.selectByUsername(userName);

        if (entity != null) {
            return new ResponseBean(Constant.REQUEST_ERROR, StrConstant.SING_UP_HAS_ERROR);
        }

        Date createDate = new Date();

        String secret = createDate.getTime() + StrConstant.PASSWORD_SALT;
        String handlePassword = TransUtil.encrypt(password, secret);

        entity = new UserEntity();
        entity.setPhone(phone);
        entity.setEmail(email);
        entity.setPassword(handlePassword);
        entity.setCreateTime(createDate);
        entity.setState(1);

        int count = userMapper.insert(entity);
        if (count > 0) {
            int userId = entity.getId();
            boolean roleFlag = permissionService.addUserRole(userId, 3);
            if (roleFlag) {
                return new ResponseBean(Constant.STATUS_SUCCESS, StrConstant.SUCCESS);
            }
        }
        return new ResponseBean(Constant.STATUS_ERROR, StrConstant.SYSTEM_ERROR);
    }

    @Override
    @CachePut(value = "users", key = "'userEntity'+#userEntity.id")
    public UserEntity updateUser(UserEntity userEntity) {
        int count = userMapper.updateByPrimaryKeySelective(userEntity);
        if (count > 0) {
            return userEntity;
        }
        return null;
    }

    @Override
    @CacheEvict(value = "users", key = "'userEntity'+#userId")
    public boolean deleteUser(int userId) {
        UserEntity entity = userMapper.selectByPrimaryKey(userId);
        if (entity != null) {
            entity.setState(2);
            int count = userMapper.updateByPrimaryKey(entity);
            return count > 0;
        }
        return false;
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
            String accessToken = JWTUtil.sign(entity.getId(), UUID.randomUUID().toString(), secret);
            response.setAccessToken(accessToken);
            return response;
        }
    }

    @Override
    @Cacheable(value = "users", key = "'userEntity'+#userId")
    public UserEntity findByUserId(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
