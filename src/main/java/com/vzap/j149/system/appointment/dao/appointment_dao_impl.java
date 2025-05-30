package com.vzap.j149.system.appointment.dao;

import com.vzap.j149.config.DBConfig;
import com.vzap.j149.system.appointment.model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class appointment_dao_impl implements appointment_dao {
    
    private static final Logger LOG = Logger.getLogger(appointment_dao_impl.class.getName());
    

    public Optional<Appointment> createAppointment(Appointment appointment) {
        String query = "INSERT INTO appointment(datetime, status, client, service) VALUES(?, ?, ?, ?)";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setTimestamp(1, Timestamp.valueOf(appointment.getDatetime()));
            ps.setString(2, appointment.getStatus().name());
            ps.setLong(3, appointment.getClient());
            ps.setLong(4, appointment.getService());
            
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        appointment.setAppointmentId(rs.getLong(1));
                        return Optional.of(appointment);
                    }
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to create appointment", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<Appointment> findAppointmentById(long appointmentId) {
        String query = "SELECT * FROM appointment WHERE appointmentId = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setLong(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAppointment(rs));
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to find appointment by ID", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return Optional.empty();
    }
    
    @Override
    public List<Appointment> findAppointmentsByClient(long clientId) {
        String query = "SELECT * FROM appointment WHERE client = ?";
        return executeQueryForList(query, clientId);
    }
    
    @Override
    public List<Appointment> findAppointmentsByService(long serviceId) {
        String query = "SELECT * FROM appointment WHERE service = ?";
        return executeQueryForList(query, serviceId);
    }
    

    public List<Appointment> findAppointmentsByStatus(Appointment.AppointmentStatus status) {
        String query = "SELECT * FROM appointment WHERE status = ?";
        List<Appointment> appointments = new ArrayList<>();
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, status.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to find appointments by status", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return appointments;
    }
    
    @Override
    public List<Appointment> findAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        String query = "SELECT * FROM appointment WHERE datetime BETWEEN ? AND ?";
        List<Appointment> appointments = new ArrayList<>();
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to find appointments by date range", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return appointments;
    }
    
    @Override
    public List<Appointment> getAllAppointments() {
        String query = "SELECT * FROM appointment";
        List<Appointment> appointments = new ArrayList<>();
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                appointments.add(mapResultSetToAppointment(rs));
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to get all appointments", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return appointments;
    }
    
    @Override
    public Optional<Appointment> updateAppointment(Appointment appointment) {
        String query = "UPDATE appointment SET datetime = ?, status = ?, client = ?, service = ? WHERE appointmentId = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setTimestamp(1, Timestamp.valueOf(appointment.getDatetime()));
            ps.setString(2, appointment.getStatus().name());
            ps.setLong(3, appointment.getClient());
            ps.setLong(4, appointment.getService());
            ps.setLong(5, appointment.getAppointmentId());
            
            if (ps.executeUpdate() > 0) {
                return Optional.of(appointment);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to update appointment", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return Optional.empty();
    }
    
   
    public Optional<Appointment> updateAppointmentStatus(long appointmentId, Appointment.AppointmentStatus status) {
        String query = "UPDATE appointment SET status = ? WHERE appointmentId = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, status.name());
            ps.setLong(2, appointmentId);
            
            if (ps.executeUpdate() > 0) {
                return findAppointmentById(appointmentId);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to update appointment status", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<Appointment> rescheduleAppointment(long appointmentId, LocalDateTime newDateTime) {
        String query = "UPDATE appointment SET datetime = ?, status = ? WHERE appointmentId = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setTimestamp(1, Timestamp.valueOf(newDateTime));
            ps.setString(2, Appointment.AppointmentStatus.RESCHEDULED.name());
            ps.setLong(3, appointmentId);
            
            if (ps.executeUpdate() > 0) {
                return findAppointmentById(appointmentId);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to reschedule appointment", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return Optional.empty();
    }
    
    @Override
    public boolean deleteAppointment(long appointmentId) {
        String query = "DELETE FROM appointment WHERE appointmentId = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setLong(1, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to delete appointment", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return false;
    }
    
    // Helper methods
    private List<Appointment> executeQueryForList(String query, long parameter) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setLong(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to execute query for list", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        return appointments;
    }
    
    private Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        return Appointment.builder()
                .appointmentId(rs.getLong("appointmentId"))
                .datetime(rs.getTimestamp("datetime").toLocalDateTime())
                .status(Appointment.AppointmentStatus.valueOf(rs.getString("status")))
                .client(rs.getLong("client"))
                .service(rs.getLong("service"))
                .build();
    }
}