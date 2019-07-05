package nl.hsleiden.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import nl.hsleiden.model.Cart;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class CartDAO extends AbstractDAO<Cart> {
    //    private final List<User> users;
    private SessionFactory sessionFactory;

    @Inject
    public CartDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;

    }

    public Cart findCartByID(int id) {
        return get(id);
    }

    public List<Cart> findCartByUserID(int userId) {
        return list(namedQuery("Cart.FIND_BY_USER_ID").setParameter("userId", Integer.toString(userId)));
    }

    public Cart updateOrCreateCart(Cart cart) {
        return (Cart) sessionFactory.getCurrentSession().merge(cart);
    }
    public void deleteByUserID(int userId) {
        namedQuery("Cart.DELETE_BY_USER_ID").setParameter("userId", Integer.toString(userId));
    }
    public void delete(int userId, int productId){
        namedQuery("Cart.DELETE_PRODUCT").setParameter("userId", Integer.toString(userId))
                .setParameter("productId", Integer.toString(productId));
    }
}
