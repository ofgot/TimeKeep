package sir.timekeep.dao;

import org.springframework.stereotype.Repository;
import sir.timekeep.model.Group;

import java.util.List;
import java.util.Optional;

@Repository
public class GroupDao extends BaseDao<Group> {
    public GroupDao() {
        super(Group.class);
    }

    public Optional<List<Group>> findAllByCreator(Integer creatorId){
        try {
            return Optional.of(em.createQuery("SELECT e FROM " + type.getSimpleName() + " e WHERE e.groupCreator.id = :user_id", type)
                    .setParameter("user_id", creatorId)
                    .getResultList());
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
}
