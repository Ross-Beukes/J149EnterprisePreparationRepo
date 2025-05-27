package com.vzap.j149.system.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    private Long serviceId;
    private String serviceName;
    private String description;
    private Integer duration;
    private Double cost;
}