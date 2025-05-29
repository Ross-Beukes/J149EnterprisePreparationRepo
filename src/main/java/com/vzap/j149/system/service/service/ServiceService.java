package com.vzap.j149.system.service.service;

import com.vzap.j149.system.service.dto.ServiceRequest;
import com.vzap.j149.system.service.dto.ServiceResponse;
import com.vzap.j149.system.service.model.Service;
import com.vzap.j149.system.service.repo.ServiceRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for handling business logic related to services.
 */
@ApplicationScoped
public class ServiceService {

    @Inject
    ServiceRepo serviceRepo;

    /**
     * Retrieves all services with pagination.
     * @param page the page number (0-based)
     * @param size the page size
     * @return list of service responses
     */
    public List<ServiceResponse> findAll(int page, int size) {
        return serviceRepo.findAll(page, size).stream()
                .map(ServiceResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a service by ID.
     * @param id the service ID
     * @return the service response
     * @throws NotFoundException if service is not found
     */
    public ServiceResponse findById(Long id) {
        return serviceRepo.findById(id)
                .map(ServiceResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Service not found with id: " + id));
    }

    /**
     * Creates a new service.
     * @param request the service request
     * @return the created service response
     */
    @Transactional
    public ServiceResponse create(ServiceRequest request) {
        Service service = new Service();
        updateServiceFromRequest(service, request);
        return ServiceResponse.fromEntity(serviceRepo.save(service)
                .orElseThrow(() -> new IllegalStateException("Failed to create service")));
    }

    /**
     * Updates an existing service.
     * @param id the service ID
     * @param request the service request
     * @return the updated service response
     * @throws NotFoundException if service is not found
     */
    @Transactional
    public ServiceResponse update(Long id, ServiceRequest request) {
        return serviceRepo.findById(id)
                .map(service -> {
                    updateServiceFromRequest(service, request);
                    return serviceRepo.update(service)
                            .map(ServiceResponse::fromEntity)
                            .orElseThrow(() -> new IllegalStateException("Failed to update service with id: " + id));
                })
                .orElseThrow(() -> new NotFoundException("Service not found with id: " + id));
    }

    /**
     * Deletes a service by ID.
     * @param id the service ID
     * @return true if deleted, false if not found
     */
    @Transactional
    public boolean delete(Long id) {
        return serviceRepo.delete(id);
    }

    /**
     * Checks if a service exists by ID.
     * @param id the service ID
     * @return true if exists, false otherwise
     */
    public boolean existsById(Long id) {
        return serviceRepo.existsById(id);
    }

    /**
     * Counts all services.
     * @return the total number of services
     */
    public long count() {
        return serviceRepo.count();
    }

    /**
     * Updates service entity from request DTO.
     * @param service the service entity to update
     * @param request the request DTO
     */
    private void updateServiceFromRequest(Service service, ServiceRequest request) {
        service.setServiceName(request.serviceName());
        service.setDescription(request.description());
        service.setDuration(request.duration());
        service.setCost(request.cost());
    }
}