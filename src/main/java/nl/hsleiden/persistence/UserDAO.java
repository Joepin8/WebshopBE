package nl.hsleiden.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import javax.inject.Inject;

import io.dropwizard.hibernate.AbstractDAO;
import nl.hsleiden.model.User;
import org.hibernate.SessionFactory;

/**
 *
 * @author Peter van Vliet
 */
@Singleton
public class UserDAO extends AbstractDAO<User>
{
//    private final List<User> users;
    private SessionFactory sessionFactory;

    @Inject
    public UserDAO(SessionFactory sessionFactory)
    {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;

//        User user1 = new User();
//        user1.setUser_id(1);
//        user1.setNaam("First user");
//        user1.setPostcode("1234AB");
//        user1.setStreetnumber("12");
//        user1.setEmailAddress("first@user.com");
//        user1.setWachtwoord("first");
//        user1.setRol("GUEST");
//
//        User user2 = new User();
//        user2.setUser_id(2);
//        user2.setFullName("Second user");
//        user2.setPostcode("9876ZY");
//        user2.setStreetnumber("98");
//        user2.setEmailAddress("second@user.com");
//        user2.setWachtwoord("second");
//        user2.setRol("ADMIN");
//
//        users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
    }
    
//    public List<User> getAll()
//    {
//        return users;
//    }
    
    public User findUserByID(int id)
    {
        return get(id);
    }
    
    public User findUserByEmail (String email)
    {
        return uniqueResult(namedQuery("User.FIND_BY_EMAIL").setParameter("email", email));
    }

    public User updateOrCreateUser(User user) {
        return (User) sessionFactory.getCurrentSession().merge(user);
    }
    public List findAllUsers(){ return list(namedQuery("User.FIND_ALL"));}
    
//    public void delete(int id)
//    {
//        users.remove(id);
//    }
}
