package com.vzap.j149.system.notification.repo;

import com.vzap.j149.config.DBConfig;
import com.vzap.j149.system.notification.model.Notification;
import com.vzap.j149.system.user.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yahya
 */
public class NotificationRepoImpl implements NotificationRepo {

    private static final Logger LOG = Logger.getLogger(NotificationRepoImpl.class.getName());


    public Optional<Notification> createNotification(Notification notification) {
        String query = "INSERT INTO notification(user, message, sentAt) VALUES(?, ?, ?)";
        
        try (Connection con = DBConfig.getCon();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, notification.getUserId());
            ps.setString(2, notification.getMessage());
            ps.setTimestamp(3, notification.getSentAt());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        notification.setNotificationId(rs.getLong(1));
                        return Optional.of(notification);
                    }
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to insert notification into the database", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection class not found", ex);
        }

        return Optional.empty();
    }

    
    @Override
    public Optional<Notification> findBynotificationId(long notificationId) {
        String query = "SELECT * FROM notification WHERE notificationId = ?";
        try (Connection con = DBConfig.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setLong(1, notificationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Notification notification;
                    notification = new Notification(
                            rs.getLong("notificationId"),
                            User.builder().userId(rs.getLong("user")).build(),
                            rs.getString("message"),
                            rs.getTimestamp("sentAt")
                    );
                    return Optional.of(notification);
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to fetch notification", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database driver not found", ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Notification> updateNotification(Notification notification) {
        String query = "UPDATE notification SET user = ?, message = ?, sentAt = ? WHERE notificationId = ?";
        try (Connection con = DBConfig.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setLong(1, notification.getUserId());
            ps.setString(2, notification.getMessage());
            ps.setTimestamp(3, notification.getSentAt());
            ps.setLong(4, notification.getNotificationId());

            if (ps.executeUpdate() > 0) {
                return Optional.of(notification);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to update notification", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database driver not found", ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Notification> deleteNotification(Notification notification) {
        String query = "DELETE FROM notification WHERE notificationId = ?";
        try (Connection con = DBConfig.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setLong(1, notification.getNotificationId());

            if (ps.executeUpdate() > 0) {
                return Optional.of(notification); // Return deleted object
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to delete notification", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database driver not found", ex);
        }
        return Optional.empty();
    }

}
