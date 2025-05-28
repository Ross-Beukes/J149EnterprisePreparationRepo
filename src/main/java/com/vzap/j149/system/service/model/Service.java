package com.vzap.j149.system.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @NotBlank(message = "Service name is required")
    @Size(max = 100, message = "Service name must be less than 100 characters")
    @Column(nullable = false, length = 100)
    private String serviceName;

    @Size(max = 500, message = "Description must be less than 500 characters")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Column(nullable = false)
    private Integer duration; // in minutes

    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", message = "Cost must be a positive number")
    @Column(nullable = false, precision = 10, scale = 2)
    private Double cost;
}