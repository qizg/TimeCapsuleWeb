package com.sinwn.capsule.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinwn.capsule.constant.Constant;
import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.request.WishAddRequest;
import com.sinwn.capsule.domain.response.WishBean;
import com.sinwn.capsule.entity.WishEntity;
import com.sinwn.capsule.mapper.WishEntityMapper;
import com.sinwn.capsule.service.WishService;
import com.sinwn.capsule.utils.NumCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WishServiceImpl implements WishService {

    private final WishEntityMapper wishMapper;

    @Autowired
    public WishServiceImpl(WishEntityMapper wishMapper) {
        this.wishMapper = wishMapper;
    }

    @Override
    public boolean addWish(WishAddRequest wish) {

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
        return count > 0;
    }

    @Override
    public ResponseBean<ResultListData<WishBean>> getWishList(
            String filterName, String strPageNo, String strPageCount) {

        Page<WishBean> page = PageHelper.startPage(
                NumCheckUtil.pageNo(strPageNo), NumCheckUtil.pageCount(strPageCount));
        List<WishBean> list = wishMapper.selectWishList();


        ResponseBean<ResultListData<WishBean>> resultData
                = new ResponseBean<>(Constant.STATUS_SUCCESS, StrConstant.SUCCESS);
        resultData.setData(new ResultListData<>(page));

        return resultData;
    }

    @Override
    @Cacheable(value = "wish", key = "'wishBean'+#wishId", unless = "#result==null")
    public WishBean getWishById(int wishId) {
        return wishMapper.selectByWishId(wishId);
    }

    @Override
    @CacheEvict(value = "wish", key = "'wishBean'+#wishId")
    public boolean deleteWishById(int wishId) {
        WishEntity entity = wishMapper.selectByPrimaryKey(wishId);
        if (entity != null) {
            entity.setStatus(2);
            int count = wishMapper.updateByPrimaryKey(entity);
            return count > 0;
        }
        return false;
    }
}
