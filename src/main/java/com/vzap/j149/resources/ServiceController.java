package com.vzap.j149.resources;

import com.vzap.j149.system.service.model.Service;
import com.vzap.j149.system.service.repo.ServiceRepo;
import jakarta.ejb.EJB;
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

@Path("/services")
public class ServiceController {

    @EJB
    private ServiceRepo serviceRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getService(@PathParam("id") Long id) {
        Service service = serviceRepository.findById(id);
        if (service == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(service).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createService(Service service) {
        serviceRepository.save(service);
        return Response.status(Response.Status.CREATED).entity(service).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateService(@PathParam("id") Long id, Service service) {
        service.setServiceId(id);
        serviceRepository.update(service);
        return Response.ok(service).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteService(@PathParam("id") Long id) {
        serviceRepository.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}