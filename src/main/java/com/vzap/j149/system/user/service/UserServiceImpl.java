package com.vzap.j149.system.user.service;

import com.vzap.j149.system.user.model.User;
import com.vzap.j149.system.user.repo.UserRepo;
import com.vzap.j149.system.user.repo.UserRepoImpl;

public class UserServiceImpl implements UserService{
    private UserRepo userRepo;

    public UserServiceImpl() {
        this.userRepo = new UserRepoImpl();
    }
    
    @Override
    public User register(User user) throws Exception {
        if(user != null){
            return userRepo.createUser(user).orElseThrow(() -> new Exception("Unable to register this user, please try again later."));
        }
        throw new IllegalArgumentException("User is null");
    }

    @Override
    public User login(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
