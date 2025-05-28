package com.vzap.j149.system.AvailabilitySlot.repo;

import com.vzap.j149.system.AvailabilitySlot.model.AvailabilitySlot;

import java.sql.SQLException;
import java.util.List;

public interface AvailabilitySlotRepo {

    List<AvailabilitySlot> findAll() throws SQLException;

    AvailabilitySlot findById(Long slotId) throws SQLException;

    void save(AvailabilitySlot slot) throws SQLException;

    void update(AvailabilitySlot slot) throws SQLException;

    void delete(Long slotId) throws SQLException;
}
