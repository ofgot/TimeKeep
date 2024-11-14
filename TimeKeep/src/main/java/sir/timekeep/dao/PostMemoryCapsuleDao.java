package sir.timekeep.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class PostMemoryCapsuleDao<T> extends BaseDao<T>{
    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> type;

    protected PostMemoryCapsuleDao(Class <T> type) {
        super(type);
        this.type = type;
    }

    public Optional<List<T>> findAllByCreator(Integer creatorId){
        try {
            return Optional.of(em.createQuery("SELECT e FROM " + type.getSimpleName() + " e WHERE e.postCreator.id = :user_id", type)
                    .setParameter("user_id", creatorId)
                    .getResultList());
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
}
