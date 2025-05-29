package com.vzap.j149.system.resource.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource {
    private long resourceId;
    private String resourceName;
    private ResourceType resourceType;
    private boolean availability;
    
    public static void main(String[] args) {
        for(ResourceType type : ResourceType.values()){
            System.out.println(type.name());
        }
    }
}