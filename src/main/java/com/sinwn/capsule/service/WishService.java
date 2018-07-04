package com.sinwn.capsule.service;

import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.request.WishAddRequest;

public interface WishService {
    ResponseBean addWish(WishAddRequest wish);
}
