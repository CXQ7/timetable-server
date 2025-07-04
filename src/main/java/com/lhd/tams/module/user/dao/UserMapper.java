package com.lhd.tams.module.user.dao;

import com.lhd.tams.module.user.model.data.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户
     */
    UserDO selectByUsername(String username);

    /**
     * 新增用户（注册）
     */
    int insert(UserDO user);
}