package sir.timekeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.PostDao;
import sir.timekeep.model.*;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    private final PostDao postDao;
    private final PostDao memoryDao;
    private final PostDao capsuleDao;

    @Autowired
    public PostService(PostDao postDao, PostDao memoryDao, PostDao capsuleDao) {
        this.postDao = postDao;
        this.memoryDao = memoryDao;
        this.capsuleDao = capsuleDao;
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

    // createPost usetri radek kodu, ale na druhou stranu by se mohl zavolat createPost a post by se neulozil
    // do Memory/Capsule, coz nechceme. Private ale nastavit nejde kvuli Transactional
    @Transactional
    protected void createPost(Post post){
        Objects.requireNonNull(post);
        postDao.persist(post);
    }

    @Transactional
    public void createMemory(Post post){
        createPost(post);
        memoryDao.persist(post);
    }

    @Transactional
    public void createCapsule(Post post){
        createPost(post);
        capsuleDao.persist(post);
    }

}
