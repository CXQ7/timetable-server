package com.jvav.timetable.module.user.service.impl;

import com.jvav.timetable.module.user.dao.UserMapper;
import com.jvav.timetable.module.user.model.data.UserDO;
import com.jvav.timetable.module.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public void saveRegister(UserDO user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUserInfo(UserDO user) {
        userMapper.updateUserInfo(user);
    }
}