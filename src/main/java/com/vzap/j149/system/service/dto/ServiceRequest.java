package com.vzap.j149.system.service.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for creating or updating a service.
 */
public record ServiceRequest(
    @NotBlank(message = "Service name is required")
    @Size(min = 2, max = 100, message = "Service name must be between 2 and 100 characters")
    String serviceName,

    @Size(max = 500, message = "Description must be less than 500 characters")
    String description,

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 1440, message = "Duration cannot exceed 24 hours (1440 minutes)")
    Integer duration,

    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Cost must have maximum 10 digits before and 2 after decimal")
    Double cost
) {}