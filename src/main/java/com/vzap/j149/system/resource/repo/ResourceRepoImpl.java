package com.vzap.j149.system.resource.repo;

import com.vzap.j149.config.DBConfig;
import com.vzap.j149.system.resource.model.Resource;
import com.vzap.j149.system.resource.model.ResourceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceRepoImpl implements ResourceRepo {
    
    private static final Logger LOG = Logger.getLogger(ResourceRepoImpl.class.getName());

    @Override
    public Optional<Resource> createResource(Resource resource) {
        String query = "INSERT INTO resource(resourceName, resourceType, availability) VALUES(?, ?, ?)";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, resource.getResourceName());
            ps.setString(2, resource.getResourceType().name());
            ps.setBoolean(3, resource.isAvailability());
            
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        resource.setResourceId(rs.getLong(1));
                        return Optional.of(resource);
                    }
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to insert new resource into the database", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return Optional.empty();
    }

    @Override
    public Optional<Resource> findResourceById(long id) {
        String query = "SELECT * FROM resource WHERE resourceId = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Resource resource = Resource.builder()
                            .resourceId(rs.getLong("resourceId"))
                            .resourceName(rs.getString("resourceName"))
                            .resourceType(ResourceType.valueOf(rs.getString("resourceType")))
                            .availability(rs.getBoolean("availability"))
                            .build();
                    return Optional.of(resource);
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to find resource by ID", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return Optional.empty();
    }

    @Override
    public Optional<Resource> findResourceByName(String name) {
        String query = "SELECT * FROM resource WHERE resourceName = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Resource resource = Resource.builder()
                            .resourceId(rs.getLong("resourceId"))
                            .resourceName(rs.getString("resourceName"))
                            .resourceType(ResourceType.valueOf(rs.getString("resourceType")))
                            .availability(rs.getBoolean("availability"))
                            .build();
                    return Optional.of(resource);
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to find resource by name", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return Optional.empty();
    }

    @Override
    public List<Resource> findAllResources() {
        List<Resource> resources = new ArrayList<>();
        String query = "SELECT * FROM resource";
        
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Resource resource = Resource.builder()
                        .resourceId(rs.getLong("resourceId"))
                        .resourceName(rs.getString("resourceName"))
                        .resourceType(ResourceType.valueOf(rs.getString("resourceType")))
                        .availability(rs.getBoolean("availability"))
                        .build();
                resources.add(resource);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to retrieve all resources", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return resources;
    }

    @Override
    public List<Resource> findResourcesByType(ResourceType type) {
        List<Resource> resources = new ArrayList<>();
        String query = "SELECT * FROM resource WHERE resourceType = ?";
        
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, type.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Resource resource = Resource.builder()
                            .resourceId(rs.getLong("resourceId"))
                            .resourceName(rs.getString("resourceName"))
                            .resourceType(ResourceType.valueOf(rs.getString("resourceType")))
                            .availability(rs.getBoolean("availability"))
                            .build();
                    resources.add(resource);
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to find resources by type", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return resources;
    }

    @Override
    public List<Resource> findAvailableResources() {
        List<Resource> resources = new ArrayList<>();
        String query = "SELECT * FROM resource WHERE availability = true";
        
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Resource resource = Resource.builder()
                        .resourceId(rs.getLong("resourceId"))
                        .resourceName(rs.getString("resourceName"))
                        .resourceType(ResourceType.valueOf(rs.getString("resourceType")))
                        .availability(rs.getBoolean("availability"))
                        .build();
                resources.add(resource);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to find available resources", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return resources;
    }

    @Override
    public List<Resource> findAvailableResourcesByType(ResourceType type) {
        List<Resource> resources = new ArrayList<>();
        String query = "SELECT * FROM resource WHERE resourceType = ? AND availability = true";
        
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, type.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Resource resource = Resource.builder()
                            .resourceId(rs.getLong("resourceId"))
                            .resourceName(rs.getString("resourceName"))
                            .resourceType(ResourceType.valueOf(rs.getString("resourceType")))
                            .availability(rs.getBoolean("availability"))
                            .build();
                    resources.add(resource);
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to find available resources by type", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return resources;
    }

    @Override
    public Optional<Resource> updateResource(Resource resource) {
        String query = "UPDATE resource SET resourceName = ?, resourceType = ?, availability = ? WHERE resourceId = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, resource.getResourceName());
            ps.setString(2, resource.getResourceType().name());
            ps.setBoolean(3, resource.isAvailability());
            ps.setLong(4, resource.getResourceId());
            
            if (ps.executeUpdate() > 0) {
                return Optional.of(resource);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to update resource", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return Optional.empty();
    }

    @Override
    public Optional<Resource> updateResourceAvailability(long resourceId, boolean availability) {
        String query = "UPDATE resource SET availability = ? WHERE resourceId = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setBoolean(1, availability);
            ps.setLong(2, resourceId);
            
            if (ps.executeUpdate() > 0) {
                return findResourceById(resourceId);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to update resource availability", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return Optional.empty();
    }

    @Override
    public Optional<Resource> deleteResource(Resource resource) {
        return deleteResourceById(resource.getResourceId()) ? Optional.of(resource) : Optional.empty();
    }

    @Override
    public boolean deleteResourceById(long resourceId) {
        String query = "DELETE FROM resource WHERE resourceId = ?";
        try (Connection con = DBConfig.getCon(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setLong(1, resourceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to delete resource", ex);
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Database connection error", ex);
        }
        
        return false;
    }
}