package com.sinwn.capsule.web;

import com.sinwn.capsule.domain.ResultData;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.entity.UserEntity;
import com.sinwn.capsule.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @RequiresPermissions("userInfo:view")//权限管理;
    public ResultData<ResultListData<UserEntity>> getUserList(HttpServletRequest request) {

        String filterName = request.getParameter("filterName");
        String pageNo = request.getParameter("pageNo");
        String pageCount = request.getParameter("pageCount");

        return userService.getUserList(filterName, pageNo, pageCount);
    }

    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd(){
        return "userInfoAdd";
    }

    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(){
        return "userInfoDel";
    }
}
