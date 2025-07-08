package com.lhd.tams.module.user.model.data;

import lombok.Data;

@Data
public class UserDO {
    private Long id;
    private String username;  // 用户名（用于登录）
    private String password;  // 密码（需加密存储）
    private String role;      // 角色（如 STUDENT/TEACHER）
    private String email;     // 邮箱
    private String avatarUrl; // 头像URL
    private Boolean inSite;   // 是否开启站内提醒
    private Boolean emailReminder; // 是否开启邮件提醒
}