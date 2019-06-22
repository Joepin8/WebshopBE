package nl.hsleiden.route;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.model.User;
import nl.hsleiden.services.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Singleton
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserRoute {

    private UserService userService;

    @Inject
    public UserRoute(UserService service) {
        this.userService = service;
    }

    /**
     * List all the users on the webpage
     * @return
     */
    @GET
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public Collection listUsers() {
        return userService.getAll();
    }

    /**
     * Creates a new Employee
     * @param toCreate
     * @return
     */
    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(User toCreate) {
    userService.add(toCreate);

    }

    /**
     * Gets one specific employee with given id as parameter
     * @param id
     * @param user
     * @return
     */
    @GET
    @UnitOfWork
    @Path("/{id}")
    public User getUser(@PathParam("id") int id, @Auth User user) {
        return userService.getUserById(id);

    }

    /**
     * Updates an employee with given id as parameter
     * @param id
     * @param toUpdate
     * @param updater
     * @return
     */
    @PUT
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public User updateUser(@PathParam("id") int id, User toUpdate, @Auth User updater) {
        return userService.updateUser(id, updater, toUpdate);
    }

    @GET
    @UnitOfWork
    @Path("/login")
    public User authenticate(@Auth User user) {
        return user;
    }

}
