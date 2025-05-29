package com.vzap.j149.system.user.repo;

import com.vzap.j149.system.user.model.Message;

import java.util.ArrayList;
import java.util.Optional;


public interface MessageRepo {
    Optional<Message> createMessage(Message message);
    void sendMessage(Message message) throws Exception;
    Optional<Message> updateMessage(Message message);
    ArrayList<Message> getMessagesBySender(long senderId) throws Exception;
    ArrayList<Message> getMessagesByReceiver(long receiverId) throws Exception;


}
