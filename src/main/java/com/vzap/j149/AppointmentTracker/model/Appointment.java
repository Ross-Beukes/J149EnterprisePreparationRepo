package com.vzap.j149.AppointmentTracker.model;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Appointment {
   private int appointmentId;
   private Timestamp dateTime;
   private Status status;
   private int client;
   private int services;
   
 
    public static void main(String[] args) {
       for(Status status : Status.values()){
           status.name();
       }
    }
}
