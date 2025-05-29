/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vzap.j149.AppointmentTracker.resources;

/**
 *
 * @author acmas
 */
public class AppointmentController {
    
}




import com.vzap.j149.system.user.model.User;
import com.vzap.j149.system.user.service.UserService;
import com.vzap.j149.system.user.service.UserServiceImpl;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rossb
 */
@Path("appointment")
public class appointmentController {
    private UserService userService;
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    
    
    public UserController() {
        this.userService = new UserServiceImpl();
    }
    
    @GET
    public Response defaultMethod(){
        return Response
                .ok("<h1>You have successfully hit the User endpoints</h1>")
                .build();
    }
    
    @POST
    @Path("register-user")
    public Response registerUser(User user){
        try{
            User created = this.userService.register(user);
            return Response.ok(created).build();
        }catch(IllegalArgumentException e){
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Unable to process the user provided")
                    .build();
        }catch(Exception e){
            LOG.log(Level.SEVERE, null, e);
            return Response
                    .status(Response.Status.EXPECTATION_FAILED)
                    .entity(e.getMessage())
                    .build();
        }
    }