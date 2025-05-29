package com.vzap.j149.system.user.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private long messageId;
    private long senderId;
    private long receiverId;
    private String content;
    private Timestamp timeStamp;
}
