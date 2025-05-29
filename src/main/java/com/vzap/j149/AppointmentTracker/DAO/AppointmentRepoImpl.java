
package com.vzap.j149.AppointmentTracker.DAO;

import com.vzap.j149.AppointmentTracker.model.Appointment;
import com.vzap.j149.AppointmentTracker.model.Status;
import com.vzap.j149.config.DBConfig;
import com.vzap.j149.system.user.repo.UserRepoImpl;
import static java.nio.file.Files.list;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.*;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.*;
import static java.util.Collections.list;
/**
 *
 * @author acmas
 */
public class AppointmentRepoImpl implements AppointmentRepo {
  
    private static final Logger LOG = Logger.getLogger(UserRepoImpl.class.getName());
    
    @Override
    public Optional<Appointment> createAppointment(Appointment appointment) {
    String sql = "INSERT INTO appointment(appointmentId, datetime, status, client, service) VALUES (?,?,?,?,?)";
    try (Connection con = new DBConfig().getCon();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, appointment.getAppointmentId());
            ps.setTimestamp(2, appointment.getDateTime());
            ps.setString(3, appointment.getStatus().name());
            ps.setInt(4, appointment.getClient());
            ps.setInt(5, appointment.getServices());
           
    if(ps.executeUpdate() > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()){
                        appointment.setAppointmentId(rs.getInt(1));
                        return Optional.of(appointment);
                    }
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to insert new appointment into the database", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AppointmentRepoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Optional.empty();
    }

    @Override
    public Optional<Appointment> findAppointmentById(int appointmentId) {
        
     String sql = "SELECT * FROM appointment WHERE appointmentid = ?";
    try (Connection con = new DBConfig().getCon();
    PreparedStatement ps = con.prepareStatement(sql)) {

    ps.setInt(1,appointmentId);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
       Appointment a = Appointment.builder()
        .appointmentId(rs.getInt("author_id"))
        .dateTime(rs.getTimestamp("datetime"))
        .status(Status.valueOf(rs.getString("status")))
        .client(rs.getInt("client"))
        .services(rs.getInt("service"))
        .build();
    return Optional.of(a);
    }
    } catch (Exception e) {
    e.printStackTrace();
    }
    return Optional.empty();   
        
    }

    @Override
    public List<Appointment> findAllAppointments() {
    
   String sql = "SELECT * FROM appointment";
    List<Appointment> appointments = new ArrayList<>();
    try (Connection con = new DBConfig().getCon();
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(sql)) {

    while (rs.next()) {
    appointments.add(Appointment.builder()
        .appointmentId(rs.getInt("author_id"))
        .dateTime(rs.getTimestamp("datetime"))
        .status(Status.valueOf(rs.getString("status")))
        .client(rs.getInt("client"))
        .services(rs.getInt("service"))
        .build());
    
    }
    } catch (Exception e) {
    e.printStackTrace();
    }
    return appointments;     
        
    }

    @Override
    public Optional<Appointment> updateAppointment(Appointment appointment) {
   
    String sql = "UPDATE appointment SET datetime=?, status=?, client=?, service=? WHERE appointmentid = ?";
        try (Connection con = new DBConfig().getCon();
        PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setTimestamp(1, appointment.getDateTime());
        ps.setString(2, appointment.getStatus().name());
        ps.setInt(3, appointment.getClient());
        ps.setInt(4, appointment.getServices());

        if (ps.executeUpdate() > 0) {
        return Optional.of(appointment);
        }
        } catch (Exception e) {
        e.printStackTrace();
        }
        return Optional.empty();    
        
    }

    @Override
    public Optional<Appointment> deleteAppointment(int appointmentId) {
    
        Optional<Appointment> toDelete = findAppointmentById(appointmentId) ;
        if (toDelete.isPresent()) {
        String sql = "DELETE FROM appointment WHERE appointmentid = ?";
        try (Connection con = new DBConfig().getCon();
        PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, appointmentId);
        if (ps.executeUpdate() > 0) {
        return toDelete;
        }
     } catch (Exception e) {
        e.printStackTrace();
     }
     }
     return Optional.empty();
    }     
    
}
