package com.jvav.timetable.module.message.model.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Boolean isRead;
    private LocalDateTime createTime;
}