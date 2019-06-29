package nl.hsleiden.model;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Product.FIND_ALL",
                query = "SELECT * FROM product;",
                resultClass = Product.class
        ),
        @NamedNativeQuery(
                name = "Product.FIND_BY_NAME",
                query = "SELECT * FROM product WHERE naam = :name",
                resultClass = Product.class
        )
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;
    private String naam;
    private String omschrijving;
    private float prijs;
    private String categorie;
    @Column(name = "img_url")
    private String imgUrl;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public float getPrijs() {
        return prijs;
    }

    public void setPrijs(float prijs) {
        this.prijs = prijs;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


}
