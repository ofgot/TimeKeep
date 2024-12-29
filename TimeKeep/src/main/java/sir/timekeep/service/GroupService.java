package sir.timekeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.GroupDao;
import sir.timekeep.dao.UserDao;
import sir.timekeep.model.Group;
import sir.timekeep.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Transactional(readOnly = true)
    public Optional<Group> find(Integer id) {
        return Optional.of(groupDao.find(id));
    }

    @Transactional(readOnly = true)
    public List<Group> findAll() {
        return groupDao.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<List<Group>> findAllByCreator(Integer creatorId) {
        return groupDao.findAllByCreator(creatorId);
    }

    @Transactional
    public void persist(Group group) {
        groupDao.persist(group);
    }

    @Transactional
    public void persist(Collection<Group> groups) {
        groupDao.persist(groups);
    }

    @Transactional
    public Group update(Group group) {
        return groupDao.update(group);
    }

    @Transactional
    public void remove(Group group) {
        groupDao.remove(group);
    }

    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return groupDao.exists(id);
    }

}
