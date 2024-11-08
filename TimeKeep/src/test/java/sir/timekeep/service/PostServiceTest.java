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
import sir.timekeep.model.Memory;
import sir.timekeep.model.Post;
import sir.timekeep.model.PostType;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void persistAddsPost(){
        Post post = new Memory("Whatever", "linkToPhoto", PostType.PHOTO);
        sut.persist(post);
        assertNotNull(sut.find(post.getId()));
    }
}
