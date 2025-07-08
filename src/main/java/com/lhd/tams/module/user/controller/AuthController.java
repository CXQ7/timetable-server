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
            
            // 处理头像Base64数据
            if (registerDTO.getAvatarBase64() != null && !registerDTO.getAvatarBase64().trim().isEmpty()) {
                String base64Data = registerDTO.getAvatarBase64().trim();
                if (isValidBase64Image(base64Data)) {
                    user.setAvatarUrl(base64Data);
                } else {
                    return ApiResult.error("无效的图片格式，请上传有效的图片");
                }
            }
            
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
                             ", avatarBase64=" + (updateDTO.getAvatarBase64() != null ? "有avatarBase64数据" : "无avatarBase64数据") +
                             ", avatarUrl=" + (updateDTO.getAvatarUrl() != null ? "有avatarUrl数据" : "无avatarUrl数据"));
            
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
            
            // 处理头像数据 - 优先使用avatarBase64，其次使用avatarUrl
            String avatarData = null;
            if (updateDTO.getAvatarBase64() != null && !updateDTO.getAvatarBase64().trim().isEmpty()) {
                avatarData = updateDTO.getAvatarBase64().trim();
                System.out.println("使用avatarBase64字段，数据长度: " + avatarData.length());
            } else if (updateDTO.getAvatarUrl() != null && !updateDTO.getAvatarUrl().trim().isEmpty()) {
                avatarData = updateDTO.getAvatarUrl().trim();
                System.out.println("使用avatarUrl字段，数据长度: " + avatarData.length());
            }
            
            if (avatarData != null) {
                // 验证base64格式
                if (isValidBase64Image(avatarData)) {
                    user.setAvatarUrl(avatarData);
                    hasUpdate = true;
                    System.out.println("设置头像Base64数据成功");
                } else {
                    return ApiResult.error("无效的图片格式，请上传有效的图片");
                }
            }
            
            if (!hasUpdate) {
                return ApiResult.error("没有可更新的数据");
            }
            
            userService.updateUserInfo(user);
            
            // 返回更新后的用户信息
            UserDO updatedUser = userService.getUserByUsername(username);
            System.out.println("更新后的用户信息: 头像数据长度=" + 
                             (updatedUser.getAvatarUrl() != null ? updatedUser.getAvatarUrl().length() : 0));
            return ApiResult.success("用户信息更新成功", updatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("更新失败：" + e.getMessage());
        }
    }
    
    /**
     * 验证base64图片格式
     */
    private boolean isValidBase64Image(String base64Data) {
        if (base64Data == null || base64Data.isEmpty()) {
            return false;
        }
        
        // 检查是否是data URL格式 (data:image/...;base64,...)
        if (base64Data.startsWith("data:image/")) {
            // 提取base64部分
            String[] parts = base64Data.split(",");
            if (parts.length == 2) {
                String mimeType = parts[0];
                String base64 = parts[1];
                
                // 检查MIME类型
                if (mimeType.contains("image/jpeg") || mimeType.contains("image/png") || 
                    mimeType.contains("image/gif") || mimeType.contains("image/webp")) {
                    
                    // 检查base64编码是否有效
                    try {
                        java.util.Base64.getDecoder().decode(base64);
                        return true;
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                }
            }
        }
        
        return false;
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
    private String avatarBase64;  // 改为base64格式
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatarBase64() { return avatarBase64; }
    public void setAvatarBase64(String avatarBase64) { this.avatarBase64 = avatarBase64; }
}

// 更新用户信息DTO
class UpdateUserInfoDTO {
    private String password;
    private String email;
    private String avatarBase64;  // base64格式
    private String avatarUrl;     // 兼容原有的avatarUrl字段
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatarBase64() { return avatarBase64; }
    public void setAvatarBase64(String avatarBase64) { this.avatarBase64 = avatarBase64; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}