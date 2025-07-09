package com.lhd.tams.module.message.controller;

import com.lhd.tams.common.util.ResponseEntityUtils;
import com.lhd.tams.module.message.model.data.MessageDO;
import com.lhd.tams.module.message.model.dto.ReminderSettingsDTO;
import com.lhd.tams.module.message.model.vo.ReminderSettingsVO;
import com.lhd.tams.module.message.model.vo.UpcomingReminderVO;
import com.lhd.tams.module.message.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/message/send")
    public String sendMessage(@RequestBody MessageDO message) {
        messageService.sendMessage(message);
        return "消息发送成功！";
    }

    @GetMapping("/message/receiver/{receiverId}")
    public List<MessageDO> getMessages(@PathVariable Long receiverId) {
        return messageService.getMessagesByReceiverId(receiverId);
    }

    /**
     * 获取用户提醒设置
     */
    @GetMapping("/course-reminder/settings")
    public Object getReminderSettings(@RequestParam String username) {
        ReminderSettingsVO settings = messageService.getReminderSettings(username);
        return ResponseEntityUtils.ok(settings);
    }

    /**
     * 更新用户提醒设置
     */
    @PutMapping("/course-reminder/settings")
    public Object updateReminderSettings(@RequestParam String username, 
                                        @RequestBody ReminderSettingsDTO settings) {
        messageService.updateReminderSettings(username, settings);
        return ResponseEntityUtils.ok("提醒设置更新成功");
    }

    /**
     * 获取即将到来的课程提醒
     */
    @GetMapping("/course-reminder/upcoming")
    public Object getUpcomingReminders(@RequestParam String username) {
        List<UpcomingReminderVO> reminders = messageService.getUpcomingReminders(username);
        return ResponseEntityUtils.ok(reminders);
    }
}