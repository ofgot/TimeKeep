package sir.timekeep.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.UserDao;
import sir.timekeep.environment.Generator;
import sir.timekeep.model.Group;
import sir.timekeep.model.Role;
import sir.timekeep.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private TestEntityManager em;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService sut;

    @SpyBean
    private UserDao userDao;

    private User premiumUser;
    private User usualUser;

    @BeforeEach
    public void setUp() {
        premiumUser = Generator.generateUser();
        premiumUser.setRole(Role.PREMIUM);
        em.persist(premiumUser);

        usualUser = Generator.generateUser();
        usualUser.setRole(Role.USUAL);
        em.persist(usualUser);
    }

    @Test
    public void createGroupForPremiumUser(){
        Group group = new Group();
        group.setName("test");
        sut.createGroup(premiumUser, group);

        // logic
        assertThat(group.getGroupCreator()).isEqualTo(premiumUser);
        assertTrue(premiumUser.getCreatedGroups().contains(group));

        // check it in db
        User us = em.find(User.class, premiumUser.getId());
        assertNotNull(us.getCreatedGroups());
        assertTrue(us.getCreatedGroups().contains(group));
    }

    @Test
    public void createGroupForUsualUser(){
        Group group = new Group();
        group.setName("test");
        sut.createGroup(usualUser, group);

        assertThat(group.getGroupCreator()).isNull();
        assertNull(usualUser.getCreatedGroups());

        User us = em.find(User.class, usualUser.getId());
        assertNull(us.getCreatedGroups());
        assertNull(us.getCreatedGroups());
    }

    @Test
    public void getCreatedGroups(){
        List<Group> gr = Generator.generateGroupsForUser();
        Integer grSize = gr.size();
        System.out.println(grSize);

        for (Group group : gr) {
            group.setGroupCreator(premiumUser);
            em.persist(group);
        }

        Optional<List<Group>> groups = sut.getCreatedGroupsOfUser(premiumUser);
        System.out.println(groups.get().size());
//        assertEquals(grSize, groups.size());

//        Optional<List<Group>> groupsFromDb = userDao.findAllCreatedGroups(premiumUser);
//        System.out.println(groupsFromDb.get().size());
    }


}
