package com.vzap.j149.system.user.service;

import com.vzap.j149.system.user.model.Message;
import com.vzap.j149.system.user.repo.MessageRepo;
import com.vzap.j149.system.user.repo.MessageRepoImpl;

import java.util.List;

public class MessageServiceImpl implements MessageService {
    private final MessageRepo messageRepo = new MessageRepoImpl();

    public void sendMessage(long senderId, long receiverId, String content) throws Exception {
        Message message = new Message(senderId, receiverId, content);
        messageRepo.sendMessage(message);
    }

    public List<Message> getReceivedMessages(long receiverId) throws Exception {
        return messageRepo.getMessagesByReceiver(receiverId);
    }

    public List<Message> getSentMessages(long senderId) throws Exception {
        return messageRepo.getMessagesBySender(senderId);
    }
}
