
package com.vzap.j149.system.notification.model;

import com.vzap.j149.system.user.model.User;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


/**
 *
 * @author yahya
 */
public class Notification {
    private long notificationId;
    private User user;
    private String message;
    private Timestamp sentAt;

    public long getUserId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
