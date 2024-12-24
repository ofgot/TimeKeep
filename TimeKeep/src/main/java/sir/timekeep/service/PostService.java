package sir.timekeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.CapsuleDao;
import sir.timekeep.dao.PostDao;
import sir.timekeep.dao.MemoryDao;
import sir.timekeep.model.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostService {
    private final PostDao postDao;
    private final MemoryDao memoryDao;
    private final CapsuleDao capsuleDao;

    @Autowired
    public PostService(PostDao postDao, MemoryDao memoryDao, CapsuleDao capsuleDao) {
        this.postDao = postDao;
        this.memoryDao = memoryDao;
        this.capsuleDao = capsuleDao;
    }
        
    @Transactional(readOnly = true)
    public Optional<List<Memory>> findMemoriesByCreator(Integer creatorId) {
        return memoryDao.findAllByCreator(creatorId);
    }

    @Transactional(readOnly = true)
    public Optional<List<Capsule>> findOpenCapsulesByCreator(Integer creatorId) {
        return capsuleDao.findAllWhereCreatorIdCapsuleOpen(creatorId);
    }

    @Transactional
    public void create(Post post){
        Objects.requireNonNull(post);
        postDao.persist(post);
    }

    @Transactional(readOnly = true)
    public Optional<Post> find(Integer id) {
        return Optional.of(postDao.find(id));
    }

    @Transactional(readOnly = true)
    public Optional<List<Post>> findAll() {
        return Optional.of(postDao.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<List<Post>> findAllByCreator(Integer creatorId) {
        return postDao.findAllByCreator(creatorId);
    }

    @Transactional
    public Post update(Post post) {
        postDao.update(post);
        return post;
    }

    @Transactional
    public void remove(Post post) {
        if (post == null || !postDao.exists(post.getId())) {
            throw new IllegalArgumentException("Post not found");
        }
        postDao.remove(post);
    }

    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return postDao.exists(id);
    }
}
