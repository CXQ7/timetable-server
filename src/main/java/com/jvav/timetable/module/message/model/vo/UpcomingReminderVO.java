package com.jvav.timetable.module.message.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 即将到来的提醒VO
 */
@Data
public class UpcomingReminderVO {
 
    
    /**
     * 提醒消息
     */
    private String message;
    
    /**
     * 提醒时间
     */
    private LocalDateTime remindTime;
} 