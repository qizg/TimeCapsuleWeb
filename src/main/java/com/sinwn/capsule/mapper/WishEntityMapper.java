package com.sinwn.capsule.mapper;

import com.sinwn.capsule.config.ConfigMapper;
import com.sinwn.capsule.domain.response.WishBean;
import com.sinwn.capsule.entity.WishEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishEntityMapper extends ConfigMapper<WishEntity> {

    List<WishBean> selectWishList();
}