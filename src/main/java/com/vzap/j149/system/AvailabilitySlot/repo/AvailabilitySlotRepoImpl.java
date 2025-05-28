package com.vzap.j149.system.AvailabilitySlot.repo;

import com.vzap.j149.system.AvailabilitySlot.model.AvailabilitySlot;
import com.vzap.j149.system.AvailabilitySlot.model.RecurrenceFrequency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvailabilitySlotRepoImpl implements AvailabilitySlotRepo {

    private final Connection connection;

    public AvailabilitySlotRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<AvailabilitySlot> findAll() throws SQLException {
        List<AvailabilitySlot> slots = new ArrayList<>();
        String query = "SELECT * FROM availability_slot";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                slots.add(mapResultSetToSlot(rs));
            }
        }
        return slots;
    }

    @Override
    public AvailabilitySlot findById(Long slotId) throws SQLException {
        String query = "SELECT * FROM availability_slot WHERE slotId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, slotId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSlot(rs);
            }
        }
        return null;
    }

    @Override
    public void save(AvailabilitySlot slot) throws SQLException {
        String query = "INSERT INTO availability_slot (staff, startTime, endTime, isAvailable, recurrenceFrequency, interval, daysOfWeek, startDate, endDate) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            fillPreparedStatement(stmt, slot);
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(AvailabilitySlot slot) throws SQLException {
        String query = "UPDATE availability_slot SET staff=?, startTime=?, endTime=?, isAvailable=?, recurrenceFrequency=?, interval=?, daysOfWeek=?, startDate=?, endDate=? WHERE slotId=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            fillPreparedStatement(stmt, slot);
            stmt.setLong(10, slot.getSlotId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long slotId) throws SQLException {
        String query = "DELETE FROM availability_slot WHERE slotId=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, slotId);
            stmt.executeUpdate();
        }
    }

    private AvailabilitySlot mapResultSetToSlot(ResultSet rs) throws SQLException {
        return new AvailabilitySlot(
            rs.getLong("slotId"),
            rs.getLong("staff"),
            rs.getTimestamp("startTime"),
            rs.getTimestamp("endTime"),
            rs.getBoolean("isAvailable"),
            RecurrenceFrequency.valueOf(rs.getString("recurrenceFrequency")),
            rs.getInt("interval"),
            rs.getString("daysOfWeek"),
            rs.getDate("startDate"),
            rs.getDate("endDate")
        );
    }

    private void fillPreparedStatement(PreparedStatement stmt, AvailabilitySlot slot) throws SQLException {
        stmt.setLong(1, slot.getStaff());
        stmt.setTimestamp(2, slot.getStartTime());
        stmt.setTimestamp(3, slot.getEndTime());
        stmt.setBoolean(4, slot.getIsAvailable());
        stmt.setString(5, slot.getRecurrenceFrequency().name());
        stmt.setInt(6, slot.getInterval());
        stmt.setString(7, slot.getDaysOfWeek());
        stmt.setDate(8, slot.getStartDate());
        stmt.setDate(9, slot.getEndDate());
    }
}
