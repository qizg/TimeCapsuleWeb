package com.sinwn.capsule.service;

import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.request.WishAddRequest;
import com.sinwn.capsule.domain.response.WishBean;

public interface WishService {
    ResponseBean addWish(WishAddRequest wish);

    ResponseBean<ResultListData<WishBean>> getWishList(
            String filterName, String pageNo, String pageCount);
}
