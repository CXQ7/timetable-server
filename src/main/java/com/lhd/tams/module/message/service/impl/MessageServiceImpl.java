package com.lhd.tams.module.message.service.impl;

import com.lhd.tams.module.message.dao.MessageMapper;
import com.lhd.tams.module.message.model.data.MessageDO;
import com.lhd.tams.module.message.service.MessageService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
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
}