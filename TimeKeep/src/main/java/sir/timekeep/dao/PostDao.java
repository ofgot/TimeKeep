package sir.timekeep.dao;

import org.springframework.stereotype.Repository;
import sir.timekeep.model.Post;

@Repository
public class PostDao extends BaseDao<Post>{
    public PostDao() {super(Post.class);}
}
