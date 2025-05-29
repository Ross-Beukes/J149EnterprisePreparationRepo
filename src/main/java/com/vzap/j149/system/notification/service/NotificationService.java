
package com.vzap.j149.system.notification.service;

import com.vzap.j149.system.notification.model.Notification;
import java.util.Optional;

/**
 *
 * @author yahya
 */
public interface NotificationService {
    Optional<Notification> createNotification(Notification notification);
    Optional<Notification> findBynotificationId(long notificationId);
    Optional<Notification> updateNotification(Notification notification);
    Optional<Notification> deleteNotification(Notification notification);
}
