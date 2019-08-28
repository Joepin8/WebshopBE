package nl.hsleiden.model;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Cart.FIND_BY_USER_ID",
                query = "SELECT * FROM cart WHERE user_id = :userId",
                resultClass = Cart.class
        ),
        @NamedNativeQuery(
                name = "Cart.DELETE_BY_USER_ID",
                query = "DELETE FROM cart WHERE user_id = :userId",
                resultClass = Cart.class
        ),
        @NamedNativeQuery(
                name = "Cart.DELETE_PRODUCT",
                query = "DELETE FROM cart WHERE user_id = :userId AND product_id = :productId",
                resultClass = Cart.class
        ),
        @NamedNativeQuery(
                name = "Cart.UPDATE_PRODUCT",
                query = "UPDATE cart SET hoeveelheid = :aantal WHERE user_id = :userId AND product_id = :productId",
                resultClass = Cart.class
        )
})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int cartId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "hoeveelheid")
    private int aantal;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
