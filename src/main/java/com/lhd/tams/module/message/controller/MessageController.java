package com.lhd.tams.module.message.controller;

import com.lhd.tams.module.message.model.data.MessageDO;
import com.lhd.tams.module.message.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageDO message) {
        messageService.sendMessage(message);
        return "消息发送成功！";
    }

    @GetMapping("/receiver/{receiverId}")
    public List<MessageDO> getMessages(@PathVariable Long receiverId) {
        return messageService.getMessagesByReceiverId(receiverId);
    }
}