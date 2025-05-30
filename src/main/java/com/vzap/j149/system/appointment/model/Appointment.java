package com.vzap.j149.system.appointment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    private long appointmentId;
    private LocalDateTime datetime;
    private AppointmentStatus status;
    private long client; // Foreign key reference to users table
    private long service; // Foreign key reference to service table
    
    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        RESCHEDULED,
        COMPLETE,
        CANCELLED_BY_CLIENT,
        CANCELED_BY_ADMIN,
        NO_SHOW,
        COMPLETED,
        IN_PROGRESS
    }
}