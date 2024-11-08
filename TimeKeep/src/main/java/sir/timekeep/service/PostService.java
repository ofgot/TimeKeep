package sir.timekeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sir.timekeep.dao.PostDao;
import sir.timekeep.model.Post;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostService {
    private final PostDao postDao;

    @Autowired
    public PostService(PostDao postDao) {
        this.postDao = postDao;
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
    public void persist(Post post) {
        postDao.persist(post);
    }

    @Transactional
    public void persist(Collection<Post> posts) {
        postDao.persist(posts);
    }

    @Transactional
    public Post update(Post post) {
        return postDao.update(post);
    }

    @Transactional
    public void remove(Post post) {
        postDao.remove(post);
    }

    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return postDao.exists(id);
    }

    @Transactional
    public void createPost(Post post){
        Objects.requireNonNull(post);
        postDao.persist(post);
    }
}
