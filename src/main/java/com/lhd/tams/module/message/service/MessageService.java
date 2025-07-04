package com.lhd.tams.module.message.service;

import com.lhd.tams.module.message.model.data.MessageDO;
import java.util.List;

public interface MessageService {
    void sendMessage(MessageDO message);
    List<MessageDO> getMessagesByReceiverId(Long receiverId);
}