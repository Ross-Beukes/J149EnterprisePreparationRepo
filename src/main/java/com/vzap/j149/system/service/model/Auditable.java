package com.vzap.j149.system.service.model;

import java.time.LocalDateTime;

/**
 * Interface that defines auditing properties for JPA entities.
 * Implementing this interface allows entities to track creation and update timestamps,
 * as well as soft-delete functionality.
 */
public interface Auditable {

    /**
     * Retrieves the timestamp when the entity was created.
     * @return the creation timestamp, or null if not set
     */
    LocalDateTime getCreatedAt();

    /**
     * Sets the timestamp when the entity was created.
     * Typically called automatically by JPA life-cycle hooks.
     * @param createdAt the creation timestamp
     */
    void setCreatedAt(LocalDateTime createdAt);

    /**
     * Retrieves the timestamp when the entity was last updated.
     * @return the last update timestamp, or null if not set
     */
    LocalDateTime getUpdatedAt();

    /**
     * Sets the timestamp when the entity was last updated.
     * Typically called automatically by JPA life-cycle hooks.
     * @param updatedAt the last update timestamp
     */
    void setUpdatedAt(LocalDateTime updatedAt);

    /**
     * Retrieves the ID of the user who created the entity.
     * @return the creator's user ID, or null if not tracked
     */
    default String getCreatedBy() {
        return null;
    }

    /**
     * Sets the ID of the user who created the entity.
     * @param createdBy the creator's user ID
     */
    default void setCreatedBy(String createdBy) {
        // Default implementation does nothing
    }

    /**
     * Retrieves the ID of the user who last modified the entity.
     * @return the last modifier's user ID, or null if not tracked
     */
    default String getLastModifiedBy() {
        return null;
    }

    /**
     * Sets the ID of the user who last modified the entity.
     * @param lastModifiedBy the last modifier's user ID
     */
    default void setLastModifiedBy(String lastModifiedBy) {
        // Default implementation does nothing
    }

    /**
     * Checks if the entity has been soft-deleted.
     * @return true if the entity is marked as deleted, false otherwise
     */
    boolean isDeleted();

    /**
     * Sets the deleted status of the entity.
     * @param deleted true to mark as deleted, false otherwise
     */
    void setDeleted(boolean deleted);
}