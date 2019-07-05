package nl.hsleiden.route;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.model.Cart;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.services.CartService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Path("/cart")
@Produces(MediaType.APPLICATION_JSON)
public class CartRoute {

    private CartService cartService;

    @Inject
    public CartRoute(CartService service) {
        this.cartService = service;
    }

    /**
     * Gives list of cart items for a user
     * @param id
     * @param user
     * @return
     */
    @GET
    @UnitOfWork
    @Path("/{user_id}")
    @RolesAllowed({"ADMIN", "GUEST"})
    public List<Cart> listCarts(@PathParam("user_id") int id, @Auth User user) {
        return cartService.getCartByUserId(id);
    }

    /**
     * Creates a new Cart
     * @param toCreate
     * @return
     */
    @POST
    @UnitOfWork
    @RolesAllowed({"ADMIN", "GUEST"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void createCart(Cart toCreate) {
        cartService.add(toCreate);

    }

//    /**
//     * Gets one specific product with given id as parameter
//     * @param id
//     * @return
//     */
//    @GET
//    @UnitOfWork
//    @Path("/{id}")
//    public Product getProduct(@PathParam("id") int id) {
//        return cartService.getCartById(id);
//
//    }

    /**
     * Updates an cart with given id as parameter
     * @param id
     * @param toUpdate
     * @param updater
     * @return
     */
    @PUT
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "GUEST"})
    @Path("/{id}")
    public Cart updateProduct(@PathParam("id") int id, Cart toUpdate, @Auth User updater) {
        return cartService.updateCart(id, toUpdate, updater);
    }

    @DELETE
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "GUEST"})
    @Path("/{uId}&{pId}")
    public void deleteProduct(@PathParam("uId") int uId, @PathParam("pId") int pId, @Auth User deleter) {
        cartService.deleteProduct(uId, pId, deleter);
    }
}
