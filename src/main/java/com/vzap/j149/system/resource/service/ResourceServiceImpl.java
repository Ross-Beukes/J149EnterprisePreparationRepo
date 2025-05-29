package com.vzap.j149.system.resource.service;

import com.vzap.j149.system.resource.model.Resource;
import com.vzap.j149.system.resource.model.ResourceType;
import com.vzap.j149.system.resource.repo.ResourceRepo;
import com.vzap.j149.system.resource.repo.ResourceRepoImpl;
import java.util.List;

public class ResourceServiceImpl implements ResourceService {
    private ResourceRepo resourceRepo;

    public ResourceServiceImpl() {
        this.resourceRepo = new ResourceRepoImpl();
    }

    @Override
    public Resource createResource(Resource resource) throws Exception {
        if (resource != null) {
            // Check if resource name already exists
            if (resourceRepo.findResourceByName(resource.getResourceName()).isPresent()) {
                throw new Exception("Resource with this name already exists");
            }
            return resourceRepo.createResource(resource)
                    .orElseThrow(() -> new Exception("Unable to create this resource, please try again later."));
        }
        throw new IllegalArgumentException("Resource is null");
    }

    @Override
    public Resource findResourceById(long id) throws Exception {
        return resourceRepo.findResourceById(id)
                .orElseThrow(() -> new Exception("Resource not found with ID: " + id));
    }

    @Override
    public Resource findResourceByName(String name) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Resource name cannot be null or empty");
        }
        return resourceRepo.findResourceByName(name)
                .orElseThrow(() -> new Exception("Resource not found with name: " + name));
    }

    @Override
    public List<Resource> getAllResources() throws Exception {
        List<Resource> resources = resourceRepo.findAllResources();
        if (resources.isEmpty()) {
            throw new Exception("No resources found");
        }
        return resources;
    }

    @Override
    public List<Resource> getResourcesByType(ResourceType type) throws Exception {
        if (type == null) {
            throw new IllegalArgumentException("Resource type cannot be null");
        }
        List<Resource> resources = resourceRepo.findResourcesByType(type);
        if (resources.isEmpty()) {
            throw new Exception("No resources found for type: " + type.name());
        }
        return resources;
    }

    @Override
    public List<Resource> getAvailableResources() throws Exception {
        List<Resource> resources = resourceRepo.findAvailableResources();
        if (resources.isEmpty()) {
            throw new Exception("No available resources found");
        }
        return resources;
    }

    @Override
    public List<Resource> getAvailableResourcesByType(ResourceType type) throws Exception {
        if (type == null) {
            throw new IllegalArgumentException("Resource type cannot be null");
        }
        List<Resource> resources = resourceRepo.findAvailableResourcesByType(type);
        if (resources.isEmpty()) {
            throw new Exception("No available resources found for type: " + type.name());
        }
        return resources;
    }

    @Override
    public Resource updateResource(Resource resource) throws Exception {
        if (resource == null) {
            throw new IllegalArgumentException("Resource is null");
        }
        if (resource.getResourceId() <= 0) {
            throw new IllegalArgumentException("Invalid resource ID");
        }
        
       
        findResourceById(resource.getResourceId());
        
        return resourceRepo.updateResource(resource)
                .orElseThrow(() -> new Exception("Unable to update resource, please try again later."));
    }

    @Override
    public Resource updateResourceAvailability(long resourceId, boolean availability) throws Exception {
        if (resourceId <= 0) {
            throw new IllegalArgumentException("Invalid resource ID");
        }
        
        findResourceById(resourceId);
        
        return resourceRepo.updateResourceAvailability(resourceId, availability)
                .orElseThrow(() -> new Exception("Unable to update resource availability, please try again later."));
    }

    @Override
    public boolean deleteResource(long resourceId) throws Exception {
        if (resourceId <= 0) {
            throw new IllegalArgumentException("Invalid resource ID");
        }
        
        
        findResourceById(resourceId);
        
        if (!resourceRepo.deleteResourceById(resourceId)) {
            throw new Exception("Unable to delete resource, please try again later.");
        }
        return true;
    }
}