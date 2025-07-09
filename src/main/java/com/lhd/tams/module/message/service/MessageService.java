package com.lhd.tams.module.message.service;

import com.lhd.tams.module.message.model.data.MessageDO;
import com.lhd.tams.module.message.model.dto.ReminderSettingsDTO;
import com.lhd.tams.module.message.model.vo.ReminderSettingsVO;
import com.lhd.tams.module.message.model.vo.UpcomingReminderVO;

import java.util.List;

public interface MessageService {
    void sendMessage(MessageDO message);
    List<MessageDO> getMessagesByReceiverId(Long receiverId);
    
    /**
     * 获取用户提醒设置
     */
    ReminderSettingsVO getReminderSettings(String username);
    
    /**
     * 更新用户提醒设置
     */
    void updateReminderSettings(String username, ReminderSettingsDTO settings);
    
    /**
     * 获取即将到来的课程提醒
     */
    List<UpcomingReminderVO> getUpcomingReminders(String username);
}