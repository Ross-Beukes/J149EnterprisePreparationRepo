package com.vzap.j149.system.service.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PreRemove;
import java.time.LocalDateTime;

/**
 * JPA Entity Listener for automatically maintaining audit information.
 * Handles setting creation/update timestamps and soft-delete functionality.
 */
public class EntityAuditListener {

    /**
     * Sets the creation and update timestamps before an entity is persisted.
     * @param target the entity being persisted
     */
    @PrePersist
    public void setAuditFieldsOnCreate(Object target) {
        if (target instanceof Auditable auditable) {
            LocalDateTime now = LocalDateTime.now();
            if (auditable.getCreatedAt() == null) {
                auditable.setCreatedAt(now);
            }
            auditable.setUpdatedAt(now);
            // Initialize deleted flag if not set
            auditable.setDeleted(false);
        }
    }

    /**
     * Sets the update timestamp before an entity is updated.
     * @param target the entity being updated
     */
    @PreUpdate
    public void setAuditFieldsOnUpdate(Object target) {
        if (target instanceof Auditable auditable) {
            auditable.setUpdatedAt(LocalDateTime.now());
        }
    }

    /**
     * Handles soft-delete scenarios before an entity is removed.
     * @param target the entity being removed
     */
    @PreRemove
    public void onPreRemove(Object target) {
        if (target instanceof Auditable auditable) {
            auditable.setDeleted(true);
            auditable.setUpdatedAt(LocalDateTime.now());
            // Prevent actual deletion - this requires additional configuration in your JPA provider
            // to handle soft deletes properly
        }
    }
}