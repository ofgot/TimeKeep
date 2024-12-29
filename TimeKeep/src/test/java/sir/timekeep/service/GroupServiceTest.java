package sir.timekeep.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.GroupDao;
import sir.timekeep.dao.PostDao;
import sir.timekeep.dao.UserDao;
import sir.timekeep.environment.Generator;
import sir.timekeep.model.Group;
import sir.timekeep.model.Role;
import sir.timekeep.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class GroupServiceTest {
    @Autowired
    private TestEntityManager entityManager;

    @SpyBean
    private GroupDao groupDao;

    @SpyBean
    private UserDao userDao;

    @Autowired
    private GroupService sut;

    private Group group;

    private Group group2;

    private Group group3;

    private Group setUpGroup(){
        Group group = Generator.generateGroup();
        User creator = group.getGroupCreator();
        userDao.persist(creator);
        List<User> users = group.getUsers();
        for (User user: users){
            userDao.persist(user);
        }
        groupDao.persist(group);
        return group;
    }

    private Group setUpGroupWithCreator(User creator){
        Group group = Generator.generateGroupWithCreator(creator);
        List<User> users = group.getUsers();
        for (User user: users){
            userDao.persist(user);
        }
        userDao.persist(group.getGroupCreator());
        groupDao.persist(group);
        return group;
    }

    @BeforeEach
    public void start(){
        group = setUpGroup();
        group2 = setUpGroup();
        group3 = setUpGroup();
    }

    @Test
    public void persistCreatesGroupExistsWorks(){
        assertTrue(groupDao.exists(group.getId()));
    }

    @Test
    public void findReturnsDesiredGroup(){
        assertEquals(group, sut.find(group.getId()).get());
    }

    @Test
    public void findAllReturnsAllGroups(){
        List<Group> groups = sut.findAll();
        assertTrue(groups.contains(group));
        assertTrue(groups.contains(group2));
        assertTrue(groups.contains(group3));
    }

    @Test
    public void findAllByCreatorReturnsAllGroupsCretedByCreator(){
        User creator = Generator.generateUser();
        while (creator == group.getGroupCreator()){
            creator = Generator.generateUser();
        }
        creator.setRole(Role.PREMIUM);
        userDao.persist(creator);
        group2 = setUpGroupWithCreator(creator);
        group3 = setUpGroupWithCreator(creator);

        List<Group> groups = sut.findAllByCreator(creator.getId()).get();
        assertTrue(groups.contains(group2));
        assertTrue(groups.contains(group3));
        assertFalse(groups.contains(group));
    }

    @Test
    public void persistGroupsCreatesCollectionOfGroups(){
        Collection<Group> groups = new ArrayList<Group>();
        groups.add(group2);
        groups.add(group3);
        groupDao.persist(groups);

        List<Group> groups2 = sut.findAll();
        System.out.println(groups2.size());
        assertTrue(groups2.contains(group2));
        assertTrue(groups2.contains(group3));
        assertTrue(groups2.contains(group));
    }

    @Test
    public void removeGroupRemovesOnlyThatGroup(){
        List<Group> groups = sut.findAll();
        assertTrue(groups.contains(group));
        assertTrue(groups.contains(group2));
        assertTrue(groups.contains(group3));
        groupDao.remove(group2);

        groups = sut.findAll();
        assertTrue(groups.contains(group));
        assertFalse(groups.contains(group2));
        assertTrue(groups.contains(group3));
    }

}
