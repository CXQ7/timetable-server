package com.lhd.tams.module.message.model.dto;

import lombok.Data;

/**
 * 提醒设置DTO
 */
@Data
public class ReminderSettingsDTO {
    /**
     * 是否开启站内提醒
     */
    private Boolean inSite;
    
    /**
     * 是否开启邮件提醒
     */
    private Boolean email;

    /**
     * 用户名
     */
    private String username;
} 