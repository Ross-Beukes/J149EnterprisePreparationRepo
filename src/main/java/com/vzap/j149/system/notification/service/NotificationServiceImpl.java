
package com.vzap.j149.system.notification.service;

/**
 *
 * @author yahya
 */

import com.vzap.j149.system.notification.model.Notification;
import com.vzap.j149.system.notification.repo.NotificationRepo;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NotificationServiceImpl implements NotificationService {
    private static final Logger LOG = Logger.getLogger(NotificationServiceImpl.class.getName());
    private final NotificationRepo notificationRepo;

    public NotificationServiceImpl(NotificationRepo notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    @Override
    public Optional<Notification> createNotification(Notification notification) {
        try {
            return notificationRepo.createNotification(notification);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error creating notification", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Notification> findBynotificationId(long notificationId) {
        try {
            return notificationRepo.findBynotificationId(notificationId);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error finding notification with ID: " + notificationId, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Notification> updateNotification(Notification notification) {
        try {
            return notificationRepo.updateNotification(notification);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error updating notification", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Notification> deleteNotification(Notification notification) {
        try {
            return notificationRepo.deleteNotification(notification);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error deleting notification", e);
            return Optional.empty();
        }
    }
}

