package com.vzap.j149.resources;

import com.vzap.j149.system.service.model.Service;
import com.vzap.j149.system.service.repo.ServiceRepo;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/services")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceController {

    @EJB
    private ServiceRepo serviceRepository;

    @GET
    public Response getAllServices() {
        try {
            List<Service> services = serviceRepository.findAll();
            return Response.ok(services).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error retrieving services: " + e.getMessage())
                         .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getService(@PathParam("id") Long id) {
        try {
            if (id == null || id <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                             .entity("Invalid service ID")
                             .build();
            }

            Optional<Service> serviceOpt = serviceRepository.findById(id);
            return serviceOpt.map(service -> Response.ok(service).build())
                           .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                                                 .entity("Service not found with id: " + id)
                                                 .build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error retrieving service: " + e.getMessage())
                         .build();
        }
    }

    @POST
    public Response createService(@Valid Service service) {
        try {
            if (service.getServiceId() != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                             .entity("ID must not be provided when creating a new service")
                             .build();
            }

            Optional<Service> createdService = serviceRepository.save(service);
            return createdService.map(s -> Response.status(Response.Status.CREATED).entity(s).build())
                               .orElseGet(() -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                                     .entity("Failed to create service")
                                                     .build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error creating service: " + e.getMessage())
                         .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateService(@PathParam("id") Long id, @Valid Service service) {
        try {
            if (id == null || id <= 0 || !id.equals(service.getServiceId())) {
                return Response.status(Response.Status.BAD_REQUEST)
                             .entity("Invalid or mismatched service ID")
                             .build();
            }

            // Check if service exists
            if (!serviceRepository.findById(id).isPresent()) {
                return Response.status(Response.Status.NOT_FOUND)
                             .entity("Service not found with id: " + id)
                             .build();
            }

            Optional<Service> updatedService = serviceRepository.update(service);
            return updatedService.map(s -> Response.ok(s).build())
                               .orElseGet(() -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                                     .entity("Failed to update service")
                                                     .build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error updating service: " + e.getMessage())
                         .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteService(@PathParam("id") Long id) {
        try {
            if (id == null || id <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                             .entity("Invalid service ID")
                             .build();
            }

            // Check if service exists
            if (!serviceRepository.findById(id).isPresent()) {
                return Response.status(Response.Status.NOT_FOUND)
                             .entity("Service not found with id: " + id)
                             .build();
            }

            boolean deleted = serviceRepository.delete(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                             .entity("Failed to delete service")
                             .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error deleting service: " + e.getMessage())
                         .build();
        }
    }
}