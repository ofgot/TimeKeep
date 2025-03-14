package sir.timekeep.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.PostDao;
import sir.timekeep.dao.UserDao;
import sir.timekeep.environment.Generator;
import sir.timekeep.model.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class PostServiceTest {

    @Autowired
    private TestEntityManager em;

    @SpyBean
    private PostDao postDao;

    @SpyBean
    private UserDao userDao;

    @Autowired
    private PostService sut;

    @Test
    public void persistAddsPostValuatedByFind(){
        Post post = Generator.generateMemory();
        sut.create(post);
        assertNotNull(sut.find(post.getId()));

        Post post1 = em.find(Memory.class, post.getId());
        assertEquals(post1.getId(), post.getId());
    }

    @Test
    public void findAllReturnsAllPosts(){
        User user1 = Generator.generateUser();
        user1.setRole(Role.PREMIUM);
        userDao.persist(user1);

        for (int i = 0; i < 10; i++) {
            sut.create(Generator.generateMemory(user1));
        }
        List<Post> posts = sut.findAll().get();
        assertEquals(10, posts.size());
    }

    @Test
    public void findAllByUserReturnsOnlyUsersPost(){
        User user1 = Generator.generateUser();
        User user2 = Generator.generateUser();

        user1.setRole(Role.PREMIUM);
        user2.setRole(Role.PREMIUM);

        userDao.persist(user1);
        userDao.persist(user2);

        for (int i = 0; i < 3; i++) {
            Post memory = Generator.generateMemory(user1);
            sut.create(memory);
        }
        for (int i = 0; i < 7; i++) {
            Post memory = Generator.generateMemory(user2);
            sut.create(memory);
        }

        Optional<List<Post>> posts = sut.findAllByCreator(user1.getId());
        assertTrue(posts.isPresent());
        assertEquals(3, posts.get().size());
    }

    @Test
    public void removePostRemovesPost(){
        Post post = Generator.generateMemory();
        sut.create(post);
        assertNotNull(sut.find(post.getId()));
        sut.remove(post);
    }

    @Test
    public void addedPostExists(){
        Post post = Generator.generateMemory();
        sut.create(post);
        assertTrue(sut.exists(post.getId()));
    }

    @Test
    public void updateUpdatesPostInDatabaseDescriptionIsUpdated(){
        Post post = Generator.generateMemory();
        sut.create(post);
        assertTrue(sut.exists(post.getId()));
        String description = "New description";
        post.setDescription(description);
        sut.update(post);
        assertEquals(description, sut.find(post.getId()).get().getDescription());
    }


    @Test
    public void findMemoriesByCreator_whenCreatorExists_returnsMemoryList() {
        User creator = Generator.generateUser();
        creator.setRole(Role.PREMIUM);
        em.persist(creator);

        Memory memory1 = Generator.generateMemory(creator);
        Memory memory2 = Generator.generateMemory(creator);
        em.persist(memory1);
        em.persist(memory2);

        Optional<List<Memory>> memories = sut.findMemoriesByCreator(creator.getId());

        assertTrue(memories.isPresent());
        assertEquals(2, memories.get().size());
        assertTrue(memories.get().contains(memory1));
        assertTrue(memories.get().contains(memory2));
    }

    @Test
    public void findMemoriesByCreatorWhenCreatorDoesNotExist() {
        Optional<List<Memory>> memories = sut.findMemoriesByCreator(9999);
        assertTrue(memories.isPresent());
        assertTrue(memories.get().isEmpty());
    }

    @Test
    public void findMemoriesByCreatorWhenNoMemoriesForCreatorReturnsEmptyList() {
        User creator = Generator.generateUser();
        creator.setRole(Role.PREMIUM);
        em.persist(creator);

        Optional<List<Memory>> memories = sut.findMemoriesByCreator(creator.getId());

        assertTrue(memories.isPresent());
        assertTrue(memories.get().isEmpty());
    }

    @Test
    public void findMemoriesByCreatorReturnsOnlyMemoriesCreatedByThatCreator(){
        User creator = Generator.generateUser();
        creator.setRole(Role.USUAL);
        userDao.persist(creator);
        for (int i = 0; i < 3; i++){
            sut.create(Generator.generateMemory(creator));
        }
        User creator2 = Generator.generateUser();
        creator2.setRole(Role.USUAL);
        userDao.persist(creator2);
        for (int i = 0; i < 4; i++){
            sut.create(Generator.generateMemory(creator2));
        }
        List<Memory> memories = sut.findMemoriesByCreator(creator.getId()).get();
        assertEquals(3, memories.size());
        for (Memory memory: memories){
            assertEquals(creator, memory.getPostCreator());
        }
    }

    @Test
    public void findOpenCapsuleByCreatorReturnsOnlyCapsulesThatAreOpenAndCreatedByCreator(){
        User creator = Generator.generateUser();
        creator.setRole(Role.USUAL);
        userDao.persist(creator);
        for (int i = 0; i < 6; i++){
            if (i % 2 == 0){
                sut.create(Generator.generateOpenedCapsule(creator));
            } else {
                sut.create(Generator.generateClosedCapsule(creator));
            }
        }
        User creator2 = Generator.generateUser();
        creator2.setRole(Role.USUAL);
        userDao.persist(creator2);
        for (int i = 0; i < 4; i++){
            if (i % 2 == 0){
                sut.create(Generator.generateOpenedCapsule(creator2));
            } else {
                sut.create(Generator.generateClosedCapsule(creator2));
            }
        }

        List<Capsule> capsules = sut.findOpenCapsulesByCreator(creator.getId()).get();
        assertEquals(3, capsules.size());
        for (Capsule capsule: capsules){
            assertEquals(creator, capsule.getPostCreator());
            assertTrue(capsule.getIsOpen());
        }
    }

}
