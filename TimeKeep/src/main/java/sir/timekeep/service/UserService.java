package sir.timekeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.GroupDao;
import sir.timekeep.dao.UserDao;
import sir.timekeep.model.Group;
import sir.timekeep.model.User;
import sir.timekeep.util.Constants;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final GroupDao groupDao;
    final User currentUser = new User(); // need change latter

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder, GroupDao groupDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.groupDao = groupDao;
    }

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        user.encodePassword(passwordEncoder);
        if (user.getRole() == null) {
            user.setRole(Constants.DEFAULT_ROLE);
        }
        userDao.persist(user);
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return userDao.findByUsername(username).isPresent();
    }

    @Transactional
    public void createGroup(User user, Group group) {
        if (user.isPremium()){
            group.setGroupCreator(user);
            user.getCreatedGroups().add(group);
            groupDao.persist(group);
        }
    }

    @Transactional(readOnly = true)
    public List<Group> getGroupsOfUser(User user) {
        return user.getCreatedGroups();
    }

    @Transactional
    public void addUserToGroup(User user, Group group, User userToAdd) {
        if (user.isPremium() && group.getGroupCreator().equals(user)){
            List<User> users = group.getUsers();
            if (!users.contains(userToAdd)){
                group.getUsers().add(userToAdd);
            }
        }
    }

    @Transactional
    public void removeUserFromGroup(User user, Group group, User userToRemove) {
        if (user.isPremium() && group.getGroupCreator().equals(user)){
            List<User> users = group.getUsers();
            users.remove(userToRemove);
        }
    }
}
