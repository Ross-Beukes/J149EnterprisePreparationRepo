package com.vzap.j149.system.AvailabilitySlot.service;

import com.vzap.j149.system.AvailabilitySlot.model.AvailabilitySlot;

import java.sql.SQLException;
import java.util.List;

public interface AvailabilitySlotServiceRepo {

    List<AvailabilitySlot> getAllSlots() throws SQLException;

    AvailabilitySlot getSlotById(Long id) throws SQLException;

    void createSlot(AvailabilitySlot slot) throws SQLException;

    void updateSlot(AvailabilitySlot slot) throws SQLException;

    void deleteSlot(Long id) throws SQLException;
}

