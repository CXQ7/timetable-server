package com.lhd.tams.module.message.dao;

import com.lhd.tams.module.message.model.data.MessageDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MessageMapper {
    void insert(MessageDO message);
    List<MessageDO> selectByReceiverId(Long receiverId);
}