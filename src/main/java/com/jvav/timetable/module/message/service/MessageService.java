package com.jvav.timetable.module.message.service;

//import data.model.message.module.com.jvav.timetable.MessageDO;
import com.jvav.timetable.module.message.model.dto.ReminderSettingsDTO;
import com.jvav.timetable.module.message.model.vo.ReminderSettingsVO;
import com.jvav.timetable.module.message.model.vo.UpcomingReminderVO;

import java.util.List;

public interface MessageService {

    
    
    /**
     * 获取用户提醒设置
     */
    ReminderSettingsVO getReminderSettings(String username);
    
    /**
     * 更新用户提醒设置
     */
    void updateReminderSettings(ReminderSettingsDTO settings);
    
    /**
     * 获取即将到来的课程提醒
     */
    List<UpcomingReminderVO> getUpcomingReminders(String username);
}