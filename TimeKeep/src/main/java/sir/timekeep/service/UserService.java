package sir.timekeep.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.GroupDao;
import sir.timekeep.dao.UserDao;
import sir.timekeep.exception.UserNotAllowedException;
import sir.timekeep.exception.ValidationException;
import sir.timekeep.model.Group;
import sir.timekeep.model.Role;
import sir.timekeep.model.User;
import sir.timekeep.util.Constants;

import java.util.*;

@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final GroupDao groupDao;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder, GroupDao groupDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.groupDao = groupDao;
    }

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);

        if (userDao.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException("User with this username already exists");
        }

        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new UserNotAllowedException("User with this email already exists");
        }

        if (user.getRole() == null) {
            user.setRole(Constants.DEFAULT_ROLE);
        }

        user.encodePassword(passwordEncoder);
        userDao.persist(user);
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return userDao.findByUsername(username).isPresent();
    }

    @Transactional(readOnly = true)
    public Optional<User> find(Integer id) {
        return Optional.of(userDao.find(id));
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional
    public void changeUserToPremium(Integer id) {
        User user = Optional.ofNullable(userDao.find(id))
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        user.setRole(Role.PREMIUM);
        userDao.update(user);
    }

    @Transactional
    public void createGroup(User user, Group group) {
        if (user.isPremium()) {
            if (user.getCreatedGroups() == null) {
                user.setCreatedGroups(new ArrayList<>());
            }
            group.setGroupCreator(user);
            user.getCreatedGroups().add(group);
            groupDao.persist(group);
        } else {
            throw new UserNotAllowedException(user + "is not allowed to create groups");
        }
    }

    @Transactional(readOnly = true)
    public Optional<List<Group>> getCreatedGroupsOfUser(User user) {
        if (user.isPremium()) {
            List<Group> groups = userDao.findAllCreatedGroups(user).orElse(Collections.emptyList());
            return Optional.of(groups);
        } else {
            throw new UserNotAllowedException(user + " is not allowed to have groups");
        }
    }

    @Transactional
    public void addUserToGroup(User user, Group group, User userToAdd) {
        if (user.isPremium() && group.getGroupCreator().equals(user)) {
            if (group.getUsers() == null) {
                group.setUsers(new ArrayList<>());
            }
            if (!group.getUsers().contains(userToAdd)) {
                group.addUser(userToAdd);
            }
        } else {
            throw new UserNotAllowedException(user + " is not allowed to add users to group");
        }
    }

    @Transactional
    public void removeUserFromGroup(User user, Group group, User userToRemove) {
        if (user.isPremium() && group.getGroupCreator().equals(user)) {
            List<User> users = group.getUsers();
            if (users != null && users.contains(userToRemove)) {
                group.removeUser(userToRemove);
                groupDao.update(group);
            }
        } else {
            throw new UserNotAllowedException(user + " is not allowed to remove users from group");
        }
    }
}
