package com.vzap.j149.system.user.repo;

import com.vzap.j149.config.DBConfig;
import com.vzap.j149.system.user.model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class MessageRepoImpl implements MessageRepo{
    private final Connection con;
    {
        try {
            con = DBConfig.getCon();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
    public void sendMessage(Message message) throws Exception {
        String sql = "INSERT INTO message (sender, receiver, content, time_stamp) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, message.getSenderId());
            ps.setLong(2, message.getReceiverId());
            ps.setString(3, message.getContent());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) message.setMessageId(rs.getLong(1));
            }
        }
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

    private ArrayList<Message> getMessages(String query, long userId) throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Message message = new Message();
                    message.setMessageId(rs.getLong("messageId"));
                    message.setSenderId(rs.getLong("sender"));
                    message.setReceiverId(rs.getLong("receiver"));
                    message.setContent(rs.getString("content"));
                    message.setTimestamp(rs.getTimestamp("time_stamp"));
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    @Override
    public ArrayList<Message> getMessagesBySender(long senderId) throws Exception {
        return getMessages("SELECT * FROM message WHERE sender = ? ORDER BY time_stamp DESC", senderId);
    }

    @Override
    public ArrayList<Message> getMessagesByReceiver(long receiverId) throws Exception {
        return getMessages("SELECT * FROM message WHERE receiver = ? ORDER BY time_stamp DESC", receiverId);
    }

}
