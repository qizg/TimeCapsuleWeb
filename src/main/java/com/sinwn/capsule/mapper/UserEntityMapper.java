package com.sinwn.capsule.mapper;

import com.github.pagehelper.Page;
import com.sinwn.capsule.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    Page<UserEntity> selectUsers(@Param("filterName") String filterName);
}