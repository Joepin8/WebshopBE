package nl.hsleiden.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import nl.hsleiden.model.Product;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ProductDAO extends AbstractDAO<Product>
{
    //    private final List<User> users;
    private SessionFactory sessionFactory;

    @Inject
    public ProductDAO(SessionFactory sessionFactory)
    {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;

    }

    public Product findProductByID(int id)
    {
        return get(id);
    }

    public Product findProductByName (String name)
    {
        return uniqueResult(namedQuery("Product.FIND_BY_NAME").setParameter("naam", name));
    }

    public Product updateOrCreateProduct(Product product) {
        return (Product) sessionFactory.getCurrentSession().merge(product);
    }
    public List findAllProducts(){ return list(namedQuery("Product.FIND_ALL"));}

//    public void delete(int id)
//    {
//        users.remove(id);
//    }
}

