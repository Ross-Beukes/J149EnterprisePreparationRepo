package com.vzap.j149.system.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a service entity in the system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "services",
        indexes = {
                @Index(columnList = "serviceName", unique = true),
                @Index(columnList = "cost"),
                @Index(columnList = "duration"),
                @Index(columnList = "deleted")
        })
@EntityListeners(EntityAuditListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Service implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private Long serviceId;

    @NotBlank(message = "Service name is required")
    @Size(min = 2, max = 100, message = "Service name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    @JsonView(Views.Public.class)
    private String serviceName;

    @Size(max = 500, message = "Description must be less than 500 characters")
    @Column(length = 500)
    @JsonView(Views.Public.class)
    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 1440, message = "Duration cannot exceed 24 hours (1440 minutes)")
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private Integer duration; // in minutes

    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Cost must have maximum 10 digits before and 2 after decimal")
    @Column(nullable = false, precision = 10, scale = 2)
    @JsonView(Views.Public.class)
    private BigDecimal cost;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonView(Views.Internal.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @JsonView(Views.Internal.class)
    private LocalDateTime updatedAt;

    @Version
    @JsonView(Views.Internal.class)
    private Long version;

    @Column(nullable = false)
    @Builder.Default
    @JsonView(Views.Internal.class)
    private boolean deleted = false;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Marks the service as deleted (soft delete)
     * @return the current service instance for method chaining
     */
    public Service markAsDeleted() {
        this.deleted = true;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    /**
     * Restores a soft-deleted service
     * @return the current service instance for method chaining
     */
    public Service restore() {
        this.deleted = false;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    /**
     * Creates a copy of this service with the given ID
     * @param id the new ID
     * @return a new Service instance with the same properties
     */
    public Service withId(Long id) {
        return Service.builder()
                .serviceId(id)
                .serviceName(this.serviceName)
                .description(this.description)
                .duration(this.duration)
                .cost(this.cost)
                .build();
    }

    /**
     * View definitions for JSON serialization
     */
    public static class Views {
        public static class Public {}
        public static class Internal extends Public {}
    }
}