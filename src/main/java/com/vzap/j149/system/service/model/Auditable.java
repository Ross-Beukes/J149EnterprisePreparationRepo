package com.vzap.j149.system.service.model;

import java.time.LocalDateTime;

/**
 * Interface that defines auditing properties for JPA entities.
 * Implementing this interface allows entities to track creation and update timestamps.
 */
public interface Auditable {

    /**
     * Retrieves the timestamp when the entity was created.
     * @return the creation timestamp, or null if not set
     */
    LocalDateTime getCreatedAt();

    /**
     * Sets the timestamp when the entity was created.
     * Typically called automatically by JPA lifecycle hooks.
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
     * Typically called automatically by JPA lifecycle hooks.
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
     * Checks if the entity is new (has not been persisted yet).
     * @return true if the entity is new, false otherwise
     */
    default boolean isNew() {
        return getCreatedAt() == null;
    }

    /**
     * Updates the audit fields with the current timestamp.
     * Should be called before persisting or updating the entity.
     * @param currentUser the ID of the current user, can be null
     * @return the current timestamp that was set
     */
    default LocalDateTime touch(String currentUser) {
        LocalDateTime now = LocalDateTime.now();
        if (isNew()) {
            setCreatedAt(now);
            if (currentUser != null) {
                setCreatedBy(currentUser);
            }
        }
        setUpdatedAt(now);
        if (currentUser != null) {
            setLastModifiedBy(currentUser);
        }
        return now;
    }
}