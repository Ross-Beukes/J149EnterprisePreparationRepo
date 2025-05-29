package com.vzap.j149.resources;

import com.vzap.j149.system.resource.model.Resource;
import com.vzap.j149.system.resource.model.ResourceType;
import com.vzap.j149.system.resource.service.ResourceService;
import com.vzap.j149.system.resource.service.ResourceServiceImpl;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("resource")
public class ResourceController {
    private ResourceService resourceService;
    private static final Logger LOG = Logger.getLogger(ResourceController.class.getName());

    public ResourceController() {
        this.resourceService = new ResourceServiceImpl();
    }

    @GET
    public Response defaultMethod() {
        return Response
                .ok("<h1>You have successfully hit the Resource endpoints</h1>")
                .build();
    }

    @POST
    @Path("create-resource")
    public Response createResource(Resource resource) {
        try {
            Resource created = this.resourceService.createResource(resource);
            return Response.ok(created).build();
        } catch (IllegalArgumentException e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Unable to process the resource provided")
                    .build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.EXPECTATION_FAILED)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("find-resource/{id}")
    public Response findResourceById(@PathParam("id") long id) {
        try {
            Resource resource = this.resourceService.findResourceById(id);
            return Response.ok(resource).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("find-resource-by-name")
    public Response findResourceByName(@QueryParam("name") String name) {
        try {
            Resource resource = this.resourceService.findResourceByName(name);
            return Response.ok(resource).build();
        } catch (IllegalArgumentException e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Invalid resource name provided")
                    .build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("all-resources")
    public Response getAllResources() {
        try {
            List<Resource> resources = this.resourceService.getAllResources();
            return Response.ok(resources).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("resources-by-type")
    public Response getResourcesByType(@QueryParam("type") String type) {
        try {
            ResourceType resourceType = ResourceType.valueOf(type.toUpperCase());
            List<Resource> resources = this.resourceService.getResourcesByType(resourceType);
            return Response.ok(resources).build();
        } catch (IllegalArgumentException e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Invalid resource type provided")
                    .build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("available-resources")
    public Response getAvailableResources() {
        try {
            List<Resource> resources = this.resourceService.getAvailableResources();
            return Response.ok(resources).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("available-resources-by-type")
    public Response getAvailableResourcesByType(@QueryParam("type") String type) {
        try {
            ResourceType resourceType = ResourceType.valueOf(type.toUpperCase());
            List<Resource> resources = this.resourceService.getAvailableResourcesByType(resourceType);
            return Response.ok(resources).build();
        } catch (IllegalArgumentException e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Invalid resource type provided")
                    .build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("update-resource")
    public Response updateResource(Resource resource) {
        try {
            Resource updated = this.resourceService.updateResource(resource);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Unable to process the resource provided")
                    .build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.EXPECTATION_FAILED)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("update-availability/{id}")
    public Response updateResourceAvailability(@PathParam("id") long id, 
                                             @QueryParam("availability") boolean availability) {
        try {
            Resource updated = this.resourceService.updateResourceAvailability(id, availability);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Invalid resource ID provided")
                    .build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.EXPECTATION_FAILED)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("delete-resource/{id}")
    public Response deleteResource(@PathParam("id") long id) {
        try {
            boolean deleted = this.resourceService.deleteResource(id);
            return Response.ok("Resource deleted successfully").build();
        } catch (IllegalArgumentException e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Invalid resource ID provided")
                    .build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.EXPECTATION_FAILED)
                    .entity(e.getMessage())
                    .build();
        }
    }
}