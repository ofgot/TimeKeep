package sir.timekeep.dao;

import org.springframework.stereotype.Repository;
import sir.timekeep.model.Capsule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CapsuleDao extends BaseDao<Capsule> {

    public CapsuleDao(){super(Capsule.class);}

    public Optional<List<Capsule>> findAllWhereCreatorIdCapsuleOpen(Integer creatorId) {
        return findAllByCreator(creatorId)
                .map(capsules -> capsules.stream()
                        .filter(Capsule::isOpen)
                        .collect(Collectors.toList()))
                .filter(openCapsules -> !openCapsules.isEmpty());
    }
}
