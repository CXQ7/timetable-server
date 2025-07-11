package com.jvav.timetable.module.user.model.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("user")
public class UserDO {
    private Long id;
    private String username;  // 用户名（用于登录）
    private String password;  // 密码（需加密存储）
    private String role;      // 角色（如 STUDENT/TEACHER）
    private String email;     // 邮箱
    @TableField("avatar_url")
    private String avatarUrl; // 头像URL
    private Boolean inSite;   // 是否开启站内提醒
    private Boolean emailReminder; // 是否开启邮件提醒
}