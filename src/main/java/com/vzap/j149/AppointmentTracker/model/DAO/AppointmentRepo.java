/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vzap.j149.AppointmentTracker.model.DAO;


import com.vzap.j149.AppointmentTracker.model.entity.Appointment;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author acmas
 */
public interface AppointmentRepo 
{
        //create
    Optional<Appointment> createAppointment(Appointment appointment);
    
    //retrieve
    Optional<Appointment> findAppointmentById(int appointmentId);
    List<Appointment> findAllAppointments();
    
    //update
    Optional<Appointment> updateAppointment(Appointment appointment);
    
    //delete
    Optional<Appointment> deleteAppointment(int appointmentId);
    
}
