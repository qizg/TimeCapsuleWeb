package com.sinwn.capsule.web;

import com.sinwn.capsule.constant.Constant;
import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.request.LoginRequest;
import com.sinwn.capsule.domain.request.SignUpRequest;
import com.sinwn.capsule.domain.response.LoginResponse;
import com.sinwn.capsule.entity.UserEntity;
import com.sinwn.capsule.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseBean<LoginResponse> login(@RequestBody @Valid LoginRequest body) {

        LoginResponse response = userService.loginByUserName(body.getUserName(), body.getPassword());

        if (response == null) {
            return new ResponseBean<>(Constant.REQUEST_ERROR, StrConstant.LOGIN_ERROR);
        } else {
            return new ResponseBean<>(Constant.STATUS_SUCCESS, "Login success", response);
        }
    }

    @PostMapping("/signUp")
    public ResponseBean signUp(@RequestBody @Valid SignUpRequest request) {
        return userService.insertUser(request);
    }

    @GetMapping("/users")
    @RequiresAuthentication
    public ResponseBean<ResultListData<UserEntity>> getUserList(HttpServletRequest request) {

        String filterName = request.getParameter("filterName");
        String pageNo = request.getParameter("pageNo");
        String pageCount = request.getParameter("pageCount");

        return userService.getUserList(filterName, pageNo, pageCount);
    }

    @RequestMapping("/userAdd")
    @RequiresPermissions("view")//权限管理;
    public String userInfoAdd() {
        return "userInfoAdd";
    }

    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel() {
        return "userInfoDel";
    }
}
