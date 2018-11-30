package com.sinwn.capsule.web;

import com.sinwn.capsule.constant.Constant;
import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.request.WishAddRequest;
import com.sinwn.capsule.domain.response.WishBean;
import com.sinwn.capsule.service.WishService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        if (wishService.addWish(wish)) {
            return new ResponseBean(Constant.STATUS_SUCCESS, StrConstant.SUCCESS);
        }
        return new ResponseBean(Constant.STATUS_ERROR, StrConstant.SYSTEM_ERROR);
    }

    @GetMapping("/wishes")
    @RequiresAuthentication
    public ResponseBean<ResultListData<WishBean>> getWishList(HttpServletRequest request) {

        String filterName = request.getParameter("filterName");
        String pageNo = request.getParameter("pageNo");
        String pageCount = request.getParameter("pageCount");

        return wishService.getWishList(filterName, pageNo, pageCount);
    }

    @GetMapping("/wish/{wishId}")
    @RequiresAuthentication
    public ResponseBean<WishBean> getWishDetail(@PathVariable("wishId") int wishId) {
        WishBean wishBean = wishService.getWishById(wishId);

        if (wishBean == null) {
            return new ResponseBean<>(Constant.REQUEST_ERROR, StrConstant.NO_DATA);
        }
        return new ResponseBean<>(Constant.STATUS_SUCCESS, StrConstant.SUCCESS, wishBean);
    }

//    @GetMapping("/wish/del/{wishId}")
    @RequiresAuthentication
    public ResponseBean delWish(@PathVariable("wishId") int wishId) {
        boolean flag = wishService.deleteWishById(wishId);
        if (flag) {
            return new ResponseBean(Constant.STATUS_SUCCESS, StrConstant.SUCCESS);
        }
        return new ResponseBean<>(Constant.STATUS_ERROR, StrConstant.SYSTEM_ERROR);
    }

}
