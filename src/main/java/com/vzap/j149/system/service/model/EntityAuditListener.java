package com.vzap.j149.system.service.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

public class EntityAuditListener {

    @PrePersist
    public void setCreatedOn(Object target) {
        if (target instanceof Auditable) {
            Auditable auditable = (Auditable) target;
            LocalDateTime now = LocalDateTime.now();
            auditable.setCreatedAt(now);
            auditable.setUpdatedAt(now);
        }
    }

    @PreUpdate
    public void setUpdatedOn(Object target) {
        if (target instanceof Auditable) {
            Auditable auditable = (Auditable) target;
            auditable.setUpdatedAt(LocalDateTime.now());
        }
    }
}
