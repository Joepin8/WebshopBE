package nl.hsleiden.services;

import nl.hsleiden.BaseService;
import nl.hsleiden.model.Cart;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.CartDAO;
import nl.hsleiden.persistence.ProductDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.ForbiddenException;
import java.util.Collection;
import java.util.List;

@Singleton
public class CartService extends BaseService<Cart>
{
    private final CartDAO dao;

    @Inject
    public CartService(CartDAO dao)
    {
        this.dao = dao;
    }

    public List<Cart> getCartByUserId(int id)
    {
        return dao.findCartByUserID(id);
    }

//    public Cart getCartById(int id)
//    {
//        return dao.findProductByID(id);
//    }

    public void add(Cart cart)
    {
        dao.updateOrCreateCart(cart);
    }

    public void updateCart(int userId, int productId, int aantal, User creator) {
        if (userId != creator.getUser_id()) {
            throw new ForbiddenException();
        }
        dao.updateCart(userId, productId, aantal);
    }

    public void delete(int userId)
    {
        // Controleren of deze cart wel bestaat
        List<Cart> cart = dao.findCartByUserID(userId);


        dao.deleteByUserID(userId);
    }
    public void deleteProduct(int userId, int ProductId, User deleter){
        if (userId != deleter.getUser_id()) {
            throw new ForbiddenException();
        }
        dao.delete(userId, ProductId);
    }
}

