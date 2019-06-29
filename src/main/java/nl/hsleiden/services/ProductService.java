package nl.hsleiden.services;

import nl.hsleiden.BaseService;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.ProductDAO;
import nl.hsleiden.persistence.UserDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.ForbiddenException;
import java.util.Collection;

@Singleton
public class ProductService extends BaseService<User>
{
    private final ProductDAO dao;

    @Inject
    public ProductService(ProductDAO dao)
    {
        this.dao = dao;
    }

    public Collection<Product> getAll()
    {
        return dao.findAllProducts();
    }

    public Product getProductById(int id)
    {
        return dao.findProductByID(id);
    }

    public void add(Product product)
    {
        dao.updateOrCreateProduct(product);
    }

    public Product updateProduct(int id, Product newProduct, User creator) {
        Product oldProduct = dao.findProductByID(id);

        if (!creator.hasRole("ADMIN")) {
            throw new ForbiddenException();
        }

        newProduct.setProductId(id);
        return dao.updateOrCreateProduct(newProduct);
    }

//    public void delete(int id)
//    {
//        // Controleren of deze gebruiker wel bestaat
//        User user = getUserById(id);
//
//        dao.delete(id);
//    }
}

