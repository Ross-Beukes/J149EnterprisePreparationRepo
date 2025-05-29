package com.vzap.j149.system.service.repo;

import com.vzap.j149.system.service.model.Service;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Service} entities.
 */
public interface ServiceRepo {
    /**
     * Saves a service entity.
     * @param service the service to save
     * @return the saved service, or empty if an error occurs
     * @throws IllegalArgumentException if service is null
     */
    Optional<Service> save(@NotNull Service service);

    /**
     * Finds a service by its ID.
     * @param id the ID of the service to find
     * @return the found service, or empty if not found
     * @throws IllegalArgumentException if id is null or not positive
     */
    Optional<Service> findById(@Positive Long id);

    /**
     * Retrieves all services with pagination.
     * @param page the page number (0-based)
     * @param size the page size
     * @return a list of services
     * @throws IllegalArgumentException if page is negative or size is not positive
     */
    List<Service> findAll(@PositiveOrZero int page, @Positive int size);

    /**
     * Retrieves all services.
     * @return a list of all services
     */
    default List<Service> findAll() {
        return findAll(0, Integer.MAX_VALUE);
    }

    /**
     * Updates an existing service.
     * @param service the service with updated information
     * @return the updated service, or empty if not found
     * @throws IllegalArgumentException if service or its ID is null
     */
    Optional<Service> update(@NotNull Service service);

    /**
     * Deletes a service by its ID.
     * @param id the ID of the service to delete
     * @return true if the service was deleted, false otherwise
     * @throws IllegalArgumentException if id is null or not positive
     */
    boolean delete(@Positive Long id);

    /**
     * Checks if a service exists by its ID.
     * @param id the ID to check
     * @return true if a service with the given ID exists, false otherwise
     * @throws IllegalArgumentException if id is null or not positive
     */
    boolean existsById(@Positive Long id);

    /**
     * Counts the total number of services.
     * @return the total number of services
     */
    long count();
}