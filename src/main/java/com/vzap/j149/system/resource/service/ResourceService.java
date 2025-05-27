package com.vzap.j149.system.resource.service;

import com.vzap.j149.system.resource.model.Resource;
import com.vzap.j149.system.resource.model.ResourceType;
import java.util.List;

public interface ResourceService {
    Resource createResource(Resource resource) throws Exception;
    Resource findResourceById(long id) throws Exception;
    Resource findResourceByName(String name) throws Exception;
    List<Resource> getAllResources() throws Exception;
    List<Resource> getResourcesByType(ResourceType type) throws Exception;
    List<Resource> getAvailableResources() throws Exception;
    List<Resource> getAvailableResourcesByType(ResourceType type) throws Exception;
    Resource updateResource(Resource resource) throws Exception;
    Resource updateResourceAvailability(long resourceId, boolean availability) throws Exception;
    boolean deleteResource(long resourceId) throws Exception;
}