package sir.timekeep.dao;

import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import sir.timekeep.model.Group;
import sir.timekeep.model.User;

import java.util.List;
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

    public Optional<User> findByEmail(String email){
        try {
            return Optional.of(em.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<List<Group>> findAllCreatedGroups(User user){
        try {
            List<Group> groups = em.createQuery("SELECT g FROM Group g WHERE g.groupCreator = :creator", Group.class)
                    .setParameter("creator", user)
                    .getResultList();
            return Optional.of(groups);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
