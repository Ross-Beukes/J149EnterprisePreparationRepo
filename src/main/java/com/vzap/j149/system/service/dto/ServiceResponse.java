package com.vzap.j149.system.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vzap.j149.system.service.model.Service;

import java.time.LocalDateTime;

/**
 * DTO for service responses.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceResponse(
    Long serviceId,
    String serviceName,
    String description,
    Integer duration,
    Double cost,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long version
) {
    public static ServiceResponse fromEntity(Service service) {
        return new ServiceResponse(
            service.getServiceId(),
            service.getServiceName(),
            service.getDescription(),
            service.getDuration(),
            service.getCost(),
            service.getCreatedAt(),
            service.getUpdatedAt(),
            service.getVersion()
        );
    }
}