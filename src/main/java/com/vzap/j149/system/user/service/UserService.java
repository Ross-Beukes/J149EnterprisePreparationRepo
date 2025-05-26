package com.vzap.j149.system.user.service;

import com.vzap.j149.system.user.model.User;

public interface UserService {
    User register(User user)throws Exception;
    User login(User user)throws Exception;
}
