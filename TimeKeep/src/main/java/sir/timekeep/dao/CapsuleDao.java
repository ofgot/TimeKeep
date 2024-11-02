package sir.timekeep.dao;

import org.springframework.stereotype.Repository;
import sir.timekeep.model.Capsule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CapsuleDao extends BaseDao<Capsule> {

    public CapsuleDao(){super(Capsule.class);}

    public Optional<List<Capsule>> findAllWhereCreatorIdCapsuleOpen(Integer creatorId) {
        if (findAllWhereCreator(creatorId).isEmpty()) {
            return Optional.empty();
        }
        List<Capsule> allMemories = findAllWhereCreator(creatorId).get();
        List<Capsule> openMemories = new ArrayList<Capsule>();
        for (Capsule capsule : allMemories) {
            if (capsule.isOpen()){
                openMemories.add(capsule);
            }
        }
        return Optional.of(openMemories);
    }
}
