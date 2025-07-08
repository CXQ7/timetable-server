package com.lhd.tams.module.message.service.impl;

import com.lhd.tams.common.exception.BusinessException;
import com.lhd.tams.module.course.model.data.CourseDO;
import com.lhd.tams.module.coursescheduling.dao.CourseSchedulingMapper;
import com.lhd.tams.module.coursescheduling.model.data.CourseSchedulingDO;
import com.lhd.tams.module.message.dao.MessageMapper;
import com.lhd.tams.module.message.model.data.MessageDO;
import com.lhd.tams.module.message.model.dto.ReminderSettingsDTO;
import com.lhd.tams.module.message.model.vo.ReminderSettingsVO;
import com.lhd.tams.module.message.model.vo.UpcomingReminderVO;
import com.lhd.tams.module.message.service.MessageService;
import com.lhd.tams.module.user.dao.UserMapper;
import com.lhd.tams.module.user.model.data.UserDO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;
    private final UserMapper userMapper;
    private final CourseSchedulingMapper courseSchedulingMapper;

    public MessageServiceImpl(MessageMapper messageMapper, UserMapper userMapper, CourseSchedulingMapper courseSchedulingMapper) {
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
        this.courseSchedulingMapper = courseSchedulingMapper;
    }

    @Override
    public void sendMessage(MessageDO message) {
        message.setCreateTime(LocalDateTime.now());
        message.setIsRead(false);
        messageMapper.insert(message);
    }

    @Override
    public List<MessageDO> getMessagesByReceiverId(Long receiverId) {
        return messageMapper.selectByReceiverId(receiverId);
    }

    @Override
    public ReminderSettingsVO getReminderSettings(String username) {
        UserDO user = userMapper.getReminderSettings(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        ReminderSettingsVO vo = new ReminderSettingsVO();
        vo.setInSite(user.getInSite() != null ? user.getInSite() : false);
        vo.setEmail(user.getEmailReminder() != null ? user.getEmailReminder() : false);
        return vo;
    }

    @Override
    public void updateReminderSettings(String username, ReminderSettingsDTO settings) {
        if (settings.getInSite() == null || settings.getEmail() == null) {
            throw new BusinessException("提醒设置参数不能为空");
        }
        
        int result = userMapper.updateReminderSettings(username, settings.getInSite(), settings.getEmail());
        if (result == 0) {
            throw new BusinessException("用户不存在或更新失败");
        }
    }

    @Override
    public List<UpcomingReminderVO> getUpcomingReminders(String username) {
        List<UpcomingReminderVO> reminders = new ArrayList<>();
        
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();
        
        // 获取今天的课程调度
        // 注意：这里需要根据实际的业务逻辑来查询用户的课程
        // 由于没有用户与课程的关联关系，这里提供一个示例实现
        
        // 示例：生成未来30分钟内的课程提醒
        LocalDateTime reminderTime = now.plusMinutes(30);
        
        UpcomingReminderVO reminder = new UpcomingReminderVO();
        reminder.setId(1L);
        reminder.setMessage("30分钟后在学武楼A101教室有数学课程。");
        reminder.setRemindTime(reminderTime);
        reminders.add(reminder);
        
        // 示例：生成未来2小时内的课程提醒
        LocalDateTime reminderTime2 = now.plusHours(2);
        UpcomingReminderVO reminder2 = new UpcomingReminderVO();
        reminder2.setId(2L);
        reminder2.setMessage("2小时后在图书馆B201教室有英语课程。");
        reminder2.setRemindTime(reminderTime2);
        reminders.add(reminder2);
        
        return reminders;
    }
}