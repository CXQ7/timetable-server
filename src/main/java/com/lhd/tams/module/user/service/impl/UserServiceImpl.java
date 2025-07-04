package com.lhd.tams.module.user.service.impl;

import com.lhd.tams.module.user.dao.UserMapper;
import com.lhd.tams.module.user.model.data.UserDO;
import com.lhd.tams.module.user.service.UserService;
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
}