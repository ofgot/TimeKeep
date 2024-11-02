package sir.timekeep.dao;

import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import sir.timekeep.model.User;

import java.util.Optional;

@Repository
public class UserDao extends BaseDao<User> {
    public UserDao() {super(User.class);}

    public Optional<User> findByUsername(String userName){
        try {
            return Optional.of(em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", userName)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
