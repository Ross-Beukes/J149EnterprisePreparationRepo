package com.vzap.j149.system.user.repo;

import com.vzap.j149.config.DBConfig;
import com.vzap.j149.system.user.model.Message;

import java.sql.*;
import java.util.Optional;

public class MessageRepoImpl implements MessageRepo{
    @Override
    public Optional<Message> createMessage(Message message) {

        String query = "INSERT INTO message (sender, receiver, content, time_stamp) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConfig.getCon(); PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, message.getSenderId());
            ps.setLong(2, message.getReceiverId());
            ps.setString(3, message.getContent());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();
            System.out.println("Message created.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Message> updateMessage(Message message) {

        String query = "UPDATE message SET content = ?, time_stamp = ? WHERE messageId = ?";

        try (Connection con = DBConfig.getCon(); PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, message.getContent());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setLong(3, message.getMessageId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Message updated.");
            } else {
                System.out.println("Message ID not found.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
