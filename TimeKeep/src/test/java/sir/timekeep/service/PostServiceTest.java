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
import sir.timekeep.dao.PostDao;
import sir.timekeep.environment.Generator;
import sir.timekeep.model.Memory;
import sir.timekeep.model.Post;
import sir.timekeep.model.User;

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
    private TestEntityManager entityManager;

    @SpyBean
    private PostDao postDao;

    @Autowired
    private PostService sut;

    @Test
    public void persistAddsPostValuatedByFind(){
        Post post = Generator.generateMemory();
        sut.createPost(post);
        assertNotNull(sut.find(post.getId()));
    }

    @Disabled
    @Test
    public void findAllReturnsAllPosts(){
        User user1 = Generator.generateUser();
        for (int i = 0; i < 10; i++) {
            sut.createPost(Generator.generateMemory(user1));
        }
        List<Post> posts = sut.findAll();
        assertEquals(10, posts.size());
    }

    @Disabled
    @Test
    public void findAllByUserReturnsOnlyUsersPost(){
        User user1 = Generator.generateUser();
        User user2 = Generator.generateUser();
        for (int i = 0; i < 3; i++) {
            sut.createPost(Generator.generateMemory(user1));
        }
        for (int i = 0; i < 7; i++) {
            sut.createPost(Generator.generateMemory(user2));
        }
        List<Post> posts = sut.findAllByCreator(user1.getId()).get();
        assertEquals(3, posts.size());
    }

    @Test
    public void removePostRemovesPost(){
        Post post = Generator.generateMemory();
        sut.createPost(post);
        assertNotNull(sut.find(post.getId()));
        sut.remove(post);
        assertNull(sut.find(post.getId()));
    }

    @Test
    public void addedPostExists(){
        Post post = Generator.generateMemory();
        sut.createPost(post);
        assertTrue(sut.exists(post.getId()));
    }

    @Test
    public void updateUpdatesPostInDatabaseDescriptionIsUpdated(){
        Post post = Generator.generateMemory();
        sut.createPost(post);
        assertTrue(sut.exists(post.getId()));
        String description = "New description";
        post.setDescription(description);
        sut.update(post);
        assertEquals(description, sut.find(post.getId()).getDescription());
    }
}
