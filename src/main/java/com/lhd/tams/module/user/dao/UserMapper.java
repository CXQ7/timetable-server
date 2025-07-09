package com.lhd.tams.module.user.dao;

import com.lhd.tams.module.user.model.data.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 修改用户信息
     */
    int updateUserInfo(UserDO user);

    /**
     * 获取用户提醒设置
     */
    UserDO getReminderSettings(@Param("username") String username);

    /**
     * 更新用户提醒设置
     */
    int updateReminderSettings(@Param("username") String username, 
                              @Param("inSite") Boolean inSite, 
                              @Param("emailReminder") Boolean emailReminder);
}