package nl.hsleiden.route;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.services.ProductService;
import nl.hsleiden.services.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Singleton
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductRoute {

    private ProductService productService;

    @Inject
    public ProductRoute(ProductService service) {
        this.productService = service;
    }

    /**
     * List all the products on the webpage
     * @return
     */
    @GET
    @UnitOfWork
    @RolesAllowed({"ADMIN","GUEST"})
    public Collection listProducts() {
        return productService.getAll();
    }

    /**
     * Creates a new Product
     * @param toCreate
     * @return
     */
    @POST
    @UnitOfWork
    @RolesAllowed({"ADMIN","GUEST"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void createProduct(Product toCreate) {
        productService.add(toCreate);

    }

    /**
     * Gets one specific product with given id as parameter
     * @param id
     * @param user
     * @return
     */
    @GET
    @UnitOfWork
    @Path("/{id}")
    public Product getProduct(@PathParam("id") int id, @Auth User user) {
        return productService.getProductById(id);

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
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    public Product updateProduct(@PathParam("id") int id, Product toUpdate, @Auth User updater) {
        return productService.updateProduct(id, toUpdate, updater);
    }


}
