package com.vzap.j149.system.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private long userId;
    private String username, password, email;
    private Role role;
    
    public static void main(String[] args) {
       for(Role role : Role.values()){
           role.name();
       }
    }
}
