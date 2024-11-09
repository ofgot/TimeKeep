package sir.timekeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.PostDao;
import sir.timekeep.model.*;

import java.time.LocalDateTime;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    // createPost usetri radek kodu, ale na druhou stranu by se mohl zavolat createPost a post by se neulozil
    // do Memory/Capsule, coz nechceme. Private ale nastavit nejde kvuli Transactional
    @Transactional
    protected void createPost(Post post){
        Objects.requireNonNull(post);
        postDao.persist(post);
    }

    @Transactional
    public void create(Memory memory){
        createPost(memory);
        memoryDao.persist(memory);
    }

    @Transactional
    public void create(Capsule capsule){
        createPost(capsule);
        capsuleDao.persist(capsule);
    }

    @Transactional(readOnly = true)
    public Post find(Integer id) {
        return postDao.find(id);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postDao.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<List<Post>> findAllByCreator(Integer creatorId) {
        return postDao.findAllByCreator(creatorId);
    }

    @Transactional
    public Post update(Memory memory) {
        memoryDao.update(memory);
        return postDao.update(memory);
    }

    @Transactional
    public Post update(Capsule capsule) {
        capsuleDao.update(capsule);
        return postDao.update(capsule);
    }

    @Transactional
    public void remove(Capsule capsule) {
        memoryDao.remove(capsule);
        capsuleDao.remove(capsule);
    }

    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return postDao.exists(id);
    }
}
