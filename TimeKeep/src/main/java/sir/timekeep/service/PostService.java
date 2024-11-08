package sir.timekeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.PostDao;
import sir.timekeep.model.Capsule;
import sir.timekeep.model.Memory;
import sir.timekeep.model.User;
import sir.timekeep.model.Group;
import sir.timekeep.model.Media;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class PostService {
    private final PostDao postDao;

    @Autowired
    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    @Transactional
    public void createMemoryPost(String name, List<Media> media, LocalDateTime timeOfCreation, User postCreator, Group group) {
        Memory memoryPost = new Memory(name, media, timeOfCreation, postCreator, group);
        postDao.persist(memoryPost);
    }

    @Transactional
    public void createCapsulePost(String name, List<Media> media, LocalDateTime timeOfCreation, User postCreator, Group group, LocalDateTime timeOfOpening) {
        Capsule capsulePost = new Capsule(name, media, timeOfCreation, postCreator, group, timeOfOpening);
        postDao.persist(capsulePost);
    }
}
