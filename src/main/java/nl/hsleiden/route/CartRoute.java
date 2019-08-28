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


    @PUT
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "GUEST"})
    public void updateProduct(Cart cartItem, @Auth User updater) {
            cartService.updateCart(cartItem.getUserId(), cartItem.getProductId(), cartItem.getAantal(), updater);
    }

    @DELETE
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "GUEST"})
    @Path("/{uId}&{pId}")
    public void deleteProduct(@PathParam("uId") int uId, @PathParam("pId") int pId, @Auth User deleter) {
        System.out.println("uID: " + uId + "\t productId" + pId + "\t user: " + deleter.getNaam());
        cartService.deleteProduct(uId, pId, deleter);
    }
}
