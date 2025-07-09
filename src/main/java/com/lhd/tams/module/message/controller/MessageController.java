package com.lhd.tams.module.message.controller;
import com.lhd.tams.common.util.ResponseEntityUtils;
import com.lhd.tams.module.message.model.dto.ReminderSettingsDTO;
import com.lhd.tams.module.message.model.vo.ReminderSettingsVO;
import com.lhd.tams.module.message.model.vo.UpcomingReminderVO;
import com.lhd.tams.module.message.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course-reminder")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    /**
     * 获取用户提醒设置
     */
    @GetMapping("/settings")
    public Object getReminderSettings(@RequestParam String username) {
        ReminderSettingsVO settings = messageService.getReminderSettings(username);
        return ResponseEntityUtils.ok(settings);
    }

    /**
     * 更新用户提醒设置
     */
    @PutMapping("/settings")
    public Object updateReminderSettings( @RequestBody ReminderSettingsDTO settings) {
        messageService.updateReminderSettings(settings);
        return ResponseEntityUtils.ok("提醒设置更新成功");
    }

    /**
     * 获取即将到来的课程提醒
     */
    @GetMapping("/upcoming")
    public Object getUpcomingReminders(@RequestParam String username) {
        List<UpcomingReminderVO> reminders = messageService.getUpcomingReminders(username);
        return ResponseEntityUtils.ok(reminders);
    }
}