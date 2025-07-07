package com.lhd.tams.module.user.controller;

import com.lhd.tams.common.consts.ErrorCodeEnum;
import com.lhd.tams.common.util.JwtUtils;
import com.lhd.tams.common.util.ResponseEntityUtils;
import com.lhd.tams.module.user.model.data.UserDO;
import com.lhd.tams.module.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginDTO loginDTO) {
        // 1. 根据用户名查询用户
        UserDO user = userService.getUserByUsername(loginDTO.getUsername());
        if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
            return ResponseEntityUtils.badRequest("用户名或密码错误");
        }
        // 2. 生成 JWT Token
        String token = JwtUtils.generateToken(user.getUsername());
        // 3. 返回 Token
        return ResponseEntityUtils.ok(token);
    }

    @PostMapping("/user/register")
    public Object register(@RequestBody RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        UserDO exist = userService.getUserByUsername(registerDTO.getUsername());
        if (exist != null) {
            return ResponseEntityUtils.badRequest("用户名已存在");
        }
        UserDO user = new UserDO();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setEmail(registerDTO.getEmail());
        user.setRole("STUDENT"); // 默认角色
        user.setAvatarUrl(registerDTO.getAvatarUrl());
        userService.saveRegister(user);
        return ResponseEntityUtils.ok("注册成功");
    }

    @GetMapping("/user/username/{username}")
    public Object getUserByUsername(@PathVariable String username) {
        UserDO user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntityUtils.badRequest("用户不存在");
        }
        return ResponseEntityUtils.ok(user);
    }

    @PutMapping("/user/update/{username}")
    public Object updateUserInfo(@PathVariable String username, @RequestBody UpdateUserInfoDTO updateDTO) {
        UserDO user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntityUtils.badRequest("用户不存在");
        }
        if (updateDTO.getPassword() != null) {
            user.setPassword(updateDTO.getPassword());
        }
        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getAvatarUrl() != null) {
            user.setAvatarUrl(updateDTO.getAvatarUrl());
        }
        userService.updateUserInfo(user);
        return ResponseEntityUtils.ok("用户信息更新成功");
    }
}

// 登录请求参数 DTO
class LoginDTO {
    private String username;
    private String password;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

// 注册请求参数 DTO
class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private String avatarUrl;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}

// 新增DTO
class UpdateUserInfoDTO {
    private String password;
    private String email;
    private String avatarUrl;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}