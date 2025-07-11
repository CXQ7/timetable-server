package com.jvav.timetable.module.message.model.vo;

import lombok.Data;

/**
 * 提醒设置VO
 */
@Data
public class ReminderSettingsVO {
    /**
     * 是否开启站内提醒
     */
    private Boolean inSite;
    
    /**
     * 是否开启邮件提醒
     */
    private Boolean email;
} 