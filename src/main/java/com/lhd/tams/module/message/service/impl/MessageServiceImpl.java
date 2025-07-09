package com.lhd.tams.module.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhd.tams.common.exception.BusinessException;
import com.lhd.tams.module.classroom.dao.ClassroomMapper;
import com.lhd.tams.module.classroom.model.data.ClassroomDO;
import com.lhd.tams.module.course.dao.CourseMapper;
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
import com.lhd.tams.module.message.service.EmailService;
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
    private final CourseMapper courseMapper;
    private final ClassroomMapper classroomMapper;
    private final EmailService emailService;

    public MessageServiceImpl(MessageMapper messageMapper, UserMapper userMapper, 
                            CourseSchedulingMapper courseSchedulingMapper, 
                            CourseMapper courseMapper, ClassroomMapper classroomMapper,
                            EmailService emailService) {
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
        this.courseSchedulingMapper = courseSchedulingMapper;
        this.courseMapper = courseMapper;
        this.classroomMapper = classroomMapper;
        this.emailService = emailService;
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
    public void updateReminderSettings(ReminderSettingsDTO settings) {
        if (settings.getInSite() == null || settings.getEmail() == null) {
            throw new BusinessException("提醒设置参数不能为空");
        }
        
        int result = userMapper.updateReminderSettings(settings.getUsername(), settings.getInSite(), settings.getEmail());
        if (result == 0) {
            throw new BusinessException("用户不存在或更新失败");
        }
    }

    @Override
    public List<UpcomingReminderVO> getUpcomingReminders(String username) {
        List<UpcomingReminderVO> result = new ArrayList<>();
        
        // 1. 查找用户
        UserDO user = userMapper.selectByUsername(username);
        if (user == null) return result;

        // 2. 查找30分钟后即将开始的课程（基于教师ID）
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyMinutesLater = now.plusMinutes(30);
        
        QueryWrapper<CourseSchedulingDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", user.getId())
                   .eq("date", now.toLocalDate())
                   .ge("attend_time", now.toLocalTime())
                   .le("attend_time", thirtyMinutesLater.toLocalTime());
        
        List<CourseSchedulingDO> upcomingCourses = courseSchedulingMapper.selectList(queryWrapper);
        
        for (CourseSchedulingDO course : upcomingCourses) {
            // 3. 获取课程和教室信息
            CourseDO courseInfo = courseMapper.selectById(course.getCourseId());
            ClassroomDO classroomInfo = classroomMapper.selectById(course.getClassroomId());
            
            if (courseInfo != null && classroomInfo != null) {
                // 4. 拼接提醒内容
                String message = String.format("30分钟后在%s教室有%s课程。", 
                    classroomInfo.getName(), courseInfo.getName());

                // 5. 发送邮件
                if (user.getEmail() != null && user.getEmailReminder() != null && user.getEmailReminder()) {
                    emailService.sendMail(user.getEmail(), "课程提醒", message);
                }

                // 6. 返回响应
                UpcomingReminderVO vo = new UpcomingReminderVO();
                vo.setMessage(message);
                vo.setRemindTime(course.getDate().atTime(course.getAttendTime()));
                result.add(vo);
            }
        }
        
        return result;
    }
}