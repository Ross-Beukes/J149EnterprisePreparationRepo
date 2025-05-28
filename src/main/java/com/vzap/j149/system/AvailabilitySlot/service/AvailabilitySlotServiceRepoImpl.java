package com.vzap.j149.system.AvailabilitySlot.service;

import com.vzap.j149.system.AvailabilitySlot.model.AvailabilitySlot;
import com.vzap.j149.system.AvailabilitySlot.repo.AvailabilitySlotRepo;

import java.sql.SQLException;
import java.util.List;

public class AvailabilitySlotServiceRepoImpl implements AvailabilitySlotServiceRepo {
    
     private final AvailabilitySlotRepo repository;

    public AvailabilitySlotServiceRepoImpl(AvailabilitySlotRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<AvailabilitySlot> getAllSlots() throws SQLException {
        return repository.findAll();    }

    @Override
    public AvailabilitySlot getSlotById(Long id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public void createSlot(AvailabilitySlot slot) throws SQLException {
        repository.save(slot);
    }

    @Override
    public void updateSlot(AvailabilitySlot slot) throws SQLException {
        repository.update(slot);
    }

    @Override
    public void deleteSlot(Long id) throws SQLException {
        repository.delete(id);
    } 

}
