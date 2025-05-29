package com.vzap.j149.system.user.service;

import com.vzap.j149.system.user.model.Message;

import java.util.List;

public interface MessageService {

    public void sendMessage(long senderId, long receiverId, String content) throws Exception;

    public List<Message> getReceivedMessages(long receiverId) throws Exception;

    public List<Message> getSentMessages(long senderId) throws Exception;
}
