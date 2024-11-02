package sir.timekeep.dao;

import org.springframework.stereotype.Repository;
import sir.timekeep.model.Group;

@Repository
public class GroupDao extends BaseDao<Group> {
    public GroupDao() {super(Group.class);}
}
