package com.vzap.j149.system.user.repo;

import com.vzap.j149.system.user.model.Message;
import com.vzap.j149.system.user.model.User;


import java.util.Optional;

public interface MessageRepo {
    Optional<Message> createMessage(Message message);
    Optional<Message> updateMessage(Message message);
}
