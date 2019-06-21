package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import java.security.Principal;
import javax.persistence.*;

/**
 * Meer informatie over validatie:
 *  http://hibernate.org/validator/
 * 
 * @author Peter van Vliet
 */

@Entity
@Table(name = "user")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "User.FIND_ALL",
                query = "SELECT * FROM user;",
                resultClass = User.class
        ),
        @NamedNativeQuery(
                name = "User.FIND_BY_EMAIL",
                query = "SELECT * FROM user WHERE email = :email",
                resultClass = User.class
        )
})
public class User implements Principal
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long user_id;
    private String naam;
    private String voornaam;
    private String postcode;
    private int huisnummer;
    private String toevoeging;
    private String email;
    private String wachtwoord;
    private String rol;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        this.naam = naam;
    }

    public String getVoornaam()
    {
        return voornaam;
    }

    public void setVoornaam(String voornaam)
    {
        this.voornaam = voornaam;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    public int getHuisnummer()
    {
        return huisnummer;
    }

    public void setHuisnummer(int huisnummer)
    {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getWachtwoord()
    {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord)
    {
        this.wachtwoord = wachtwoord;
    }

    @Override
    @JsonIgnore
    public String getName()
    {
        return naam;
    }
    
    public String getRol()
    {
        return rol;
    }

    public void setRol(String rol)
    {
        this.rol = rol;
    }
    
    public boolean hasRole(String rol) {return this.rol.equals(rol); }
    
    public boolean equals(User user)
    {
        return email.equals(user.getEmail());
    }



}
