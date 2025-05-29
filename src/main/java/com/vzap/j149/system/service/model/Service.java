package com.vzap.j149.system.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "services", indexes = @Index(columnList = "serviceName", unique = true))
@EntityListeners(EntityAuditListener.class)
public class Service implements Auditable {
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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @Column(nullable = false)
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
     */
    public void markAsDeleted() {
        this.deleted = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Restores a soft-deleted service
     */
    public void restore() {
        this.deleted = false;
        this.updatedAt = LocalDateTime.now();
    }
}