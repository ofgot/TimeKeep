package sir.timekeep.dao;

import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import sir.timekeep.model.Post;
import sir.timekeep.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class PostDao extends BaseDao<Post>{
    public PostDao() {super(Post.class);}
}
