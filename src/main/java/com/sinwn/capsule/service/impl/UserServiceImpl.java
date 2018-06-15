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
import com.sinwn.capsule.utils.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public int updateUser(UserEntity userEntity) {
        return userMapper.updateByPrimaryKeySelective(userEntity);
    }

    @Override
    public int deleteUser(int id) {
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
            String accessToken = JWTUtil.sign(entity.getId(), UUID.randomUUID().toString(), secret);
            response.setAccessToken(accessToken);
            return response;
        }
    }

    @Override
    public UserEntity findByUserId(int userId) {
        return userMapper.selectByUserId(userId);
    }
}
