package com.sinwn.capsule.web;

import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.request.WishAddRequest;
import com.sinwn.capsule.domain.response.WishBean;
import com.sinwn.capsule.service.WishService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class WishController {

    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/addWish")
    @RequiresAuthentication
    public ResponseBean addWish(@RequestBody @Valid WishAddRequest wish) {

        return wishService.addWish(wish);
    }

    @GetMapping("/wishes")
    @RequiresAuthentication
    public ResponseBean<ResultListData<WishBean>> getUserList(HttpServletRequest request) {

        String filterName = request.getParameter("filterName");
        String pageNo = request.getParameter("pageNo");
        String pageCount = request.getParameter("pageCount");

        return wishService.getWishList(filterName, pageNo, pageCount);
    }
}
