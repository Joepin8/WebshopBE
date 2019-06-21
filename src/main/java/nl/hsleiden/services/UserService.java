package nl.hsleiden.services;

import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.ForbiddenException;

import nl.hsleiden.BaseService;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;

/**
 *
 * @author Peter van Vliet
 */
@Singleton
public class UserService extends BaseService<User>
{
    private final UserDAO dao;
    
    @Inject
    public UserService(UserDAO dao)
    {
        this.dao = dao;
    }
    
    public Collection<User> getAll()
    {
        return dao.findAllUsers();
    }
    
    public User getUserById(int id)
    {
        return dao.findUserByID(id);
    }
    
    public void add(User user)
    {
        user.setRol("GUEST");
        
        dao.updateOrCreateUser(user);
    }

    public User updateUser(int id, User newUser, User creator) {
        User oldUser = dao.findUserByID(id);

        if (!creator.hasRole("ADMIN")) {
            if (creator.getUser_id() != oldUser.getUser_id()) {
                throw new ForbiddenException();
            }
        }

        newUser.setUser_id(id);
        return dao.updateOrCreateUser(newUser);
    }
    
//    public void delete(int id)
//    {
//        // Controleren of deze gebruiker wel bestaat
//        User user = getUserById(id);
//
//        dao.delete(id);
//    }
}
