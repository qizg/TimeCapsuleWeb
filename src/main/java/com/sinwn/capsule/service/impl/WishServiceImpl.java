package com.sinwn.capsule.service.impl;

import com.sinwn.capsule.constant.Constant;
import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.request.WishAddRequest;
import com.sinwn.capsule.entity.WishEntity;
import com.sinwn.capsule.mapper.WishEntityMapper;
import com.sinwn.capsule.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WishServiceImpl implements WishService {

    private final WishEntityMapper wishMapper;

    @Autowired
    public WishServiceImpl(WishEntityMapper wishMapper) {
        this.wishMapper = wishMapper;
    }

    @Override
    public ResponseBean addWish(WishAddRequest wish) {

        WishEntity entity = new WishEntity();
        entity.setUserId(wish.getUserId());
        entity.setPublicStatus(wish.getPublicStatus());
        entity.setMatchStatus(wish.getMatchStatus());
        entity.setReceiveTime(wish.getReceiveTime());
        entity.setEmail(wish.getEmail());
        entity.setContent(wish.getContent());
        entity.setCreateTime(new Date());
        entity.setStatus(1);
        int count = wishMapper.insert(entity);
        if (count > 0) {
            return new ResponseBean(Constant.STATUS_SUCCESS, StrConstant.SUCCESS);
        }
        return new ResponseBean(Constant.STATUS_ERROR, StrConstant.SYSTEM_ERROR);
    }
}
