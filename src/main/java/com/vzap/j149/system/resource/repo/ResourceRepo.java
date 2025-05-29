package com.vzap.j149.system.resource.repo;

import com.vzap.j149.system.resource.model.Resource;
import com.vzap.j149.system.resource.model.ResourceType;
import java.util.List;
import java.util.Optional;

public interface ResourceRepo {
    
    Optional<Resource> createResource(Resource resource);
    
    
    Optional<Resource> findResourceById(long id);
    Optional<Resource> findResourceByName(String name);
    List<Resource> findAllResources();
    List<Resource> findResourcesByType(ResourceType type);
    List<Resource> findAvailableResources();
    List<Resource> findAvailableResourcesByType(ResourceType type);
    
    
    Optional<Resource> updateResource(Resource resource);
    Optional<Resource> updateResourceAvailability(long resourceId, boolean availability);
    
    
    Optional<Resource> deleteResource(Resource resource);
    boolean deleteResourceById(long resourceId);
}