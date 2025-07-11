package com.jvav.timetable.module.user.service;

import com.jvav.timetable.module.user.model.data.UserDO;

public interface UserService {
    /**
     * 根据用户名查询用户（用于登录验证）
     */
    UserDO getUserByUsername(String username);

    /**
     * 用户注册
     */
    void saveRegister(UserDO user);

    /**
     * 修改用户信息
     */
    void updateUserInfo(UserDO user);
}