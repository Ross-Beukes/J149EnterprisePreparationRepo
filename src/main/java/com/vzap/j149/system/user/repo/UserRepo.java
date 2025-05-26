package com.vzap.j149.system.user.repo;

import com.vzap.j149.system.user.model.User;
import java.util.Optional;

public interface UserRepo {
    //create
    Optional<User> createUser(User user);
    
    //retrieve
    Optional<User> findUserById(long id);
    Optional<User> findByCredentials(User user);
    
    //update
    Optional<User> updateUser(User user);
    
    //delete
    Optional<User> deleteUser(User user);
    
}
