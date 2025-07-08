package com.lhd.tams.module.user.controller;

import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.common.util.JwtUtils;
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
    public ApiResult<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            // 1. 根据用户名查询用户
            UserDO user = userService.getUserByUsername(loginDTO.getUsername());
            if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
                return ApiResult.error("用户名或密码错误");
            }
            // 2. 生成 JWT Token
            String token = JwtUtils.generateToken(user.getUsername());
            // 3. 返回 Token
            return ApiResult.success("登录成功", token);
        } catch (Exception e) {
            return ApiResult.error("登录失败：" + e.getMessage());
        }
    }

    @PostMapping("/user/register")
    public ApiResult<String> register(@RequestBody RegisterDTO registerDTO) {
        try {
            // 检查用户名是否已存在
            UserDO exist = userService.getUserByUsername(registerDTO.getUsername());
            if (exist != null) {
                return ApiResult.error("用户名已存在");
            }
            UserDO user = new UserDO();
            user.setUsername(registerDTO.getUsername());
            user.setPassword(registerDTO.getPassword());
            user.setEmail(registerDTO.getEmail());
            user.setRole("STUDENT"); // 默认角色
            user.setAvatarUrl(registerDTO.getAvatarUrl());
            userService.saveRegister(user);
            return ApiResult.success("注册成功");
        } catch (Exception e) {
            return ApiResult.error("注册失败：" + e.getMessage());
        }
    }

    @GetMapping("/user/username/{username}")
    public ApiResult<UserDO> getUserByUsername(@PathVariable String username) {
        try {
            UserDO user = userService.getUserByUsername(username);
            if (user == null) {
                return ApiResult.error("用户不存在");
            }
            return ApiResult.success("查询成功", user);
        } catch (Exception e) {
            return ApiResult.error("查询失败：" + e.getMessage());
        }
    }

    @PutMapping("/user/update/{username}")
    public ApiResult<UserDO> updateUserInfo(@PathVariable String username, @RequestBody UpdateUserInfoDTO updateDTO) {
        try {
            System.out.println("收到更新请求，用户名: " + username);
            System.out.println("更新数据: password=" + updateDTO.getPassword() + 
                             ", email=" + updateDTO.getEmail() + 
                             ", avatarUrl=" + updateDTO.getAvatarUrl());
            
            UserDO user = userService.getUserByUsername(username);
            if (user == null) {
                return ApiResult.error("用户不存在");
            }
            
            // 只更新非空字段
            boolean hasUpdate = false;
            if (updateDTO.getPassword() != null && !updateDTO.getPassword().trim().isEmpty()) {
                user.setPassword(updateDTO.getPassword().trim());
                hasUpdate = true;
            }
            if (updateDTO.getEmail() != null && !updateDTO.getEmail().trim().isEmpty()) {
                user.setEmail(updateDTO.getEmail().trim());
                hasUpdate = true;
            }
            if (updateDTO.getAvatarUrl() != null && !updateDTO.getAvatarUrl().trim().isEmpty()) {
                user.setAvatarUrl(updateDTO.getAvatarUrl().trim());
                hasUpdate = true;
                System.out.println("设置头像URL: " + updateDTO.getAvatarUrl().trim());
            }
            
            if (!hasUpdate) {
                return ApiResult.error("没有可更新的数据");
            }
            
            userService.updateUserInfo(user);
            
            // 返回更新后的用户信息
            UserDO updatedUser = userService.getUserByUsername(username);
            System.out.println("更新后的用户信息: " + updatedUser.getAvatarUrl());
            return ApiResult.success("用户信息更新成功", updatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("更新失败：" + e.getMessage());
        }
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

// 更新用户信息DTO
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