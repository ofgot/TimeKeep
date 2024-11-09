package sir.timekeep.service;

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
import sir.timekeep.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

//    @Disabled
    @Test
    public void persistCreatesGroupExistsWorks(){
        Group group = Generator.generateGroup();
        User creator = group.getGroupCreator();
        userDao.persist(creator);
        List<User> users = group.getUsers();
        for (User user: users){
            userDao.persist(user);
        }
        groupDao.persist(group);
        assertTrue(groupDao.exists(group.getId()));
    }
}
