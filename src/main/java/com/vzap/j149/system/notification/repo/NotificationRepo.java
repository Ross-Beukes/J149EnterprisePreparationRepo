
package com.vzap.j149.system.notification.repo;

import com.vzap.j149.system.notification.model.Notification;
import com.vzap.j149.system.user.model.User;
import java.util.Optional;

/**
 *
 * @author yahya
 */
public interface NotificationRepo {
 
    //create
    Optional<Notification> createNotification(Notification notification);
    
    //retrieve
    
    Optional<Notification> findBynotificationId(long notificationId);
    
    // update
    
    Optional<Notification> updateNotification(Notification notification);
    
    // delete
    
    Optional<Notification> deleteNotification(Notification notification);
    
   
    
}
