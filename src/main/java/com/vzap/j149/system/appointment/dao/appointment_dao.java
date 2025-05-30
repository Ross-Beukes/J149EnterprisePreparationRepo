package com.vzap.j149.system.appointment.dao;

import com.vzap.j149.system.appointment.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface appointment_dao {
    
    // Create
//    Optional<Appointment> createAppointment(Appointment appointment);
//
//    Optional<Appointment> createAppointment(Appointment appointment);

    // Retrieve
    Optional<Appointment> findAppointmentById(long appointmentId);
    List<Appointment> findAppointmentsByClient(long clientId);
    List<Appointment> findAppointmentsByService(long serviceId);
//    List<Appointment> findAppointmentsByStatus(Appointment.AppointmentStatus status);
//
//    List<Appointment> findAppointmentsByStatus(Appointment.AppointmentStatus status);

    List<Appointment> findAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Appointment> getAllAppointments();
    
    // Update
    Optional<Appointment> updateAppointment(Appointment appointment);
//    Optional<Appointment> updateAppointmentStatus(long appointmentId, Appointment.AppointmentStatus status);
//
//    Optional<Appointment> updateAppointmentStatus(long appointmentId, Appointment.AppointmentStatus status);

    Optional<Appointment> rescheduleAppointment(long appointmentId, LocalDateTime newDateTime);
    
    // Delete
    boolean deleteAppointment(long appointmentId);
}