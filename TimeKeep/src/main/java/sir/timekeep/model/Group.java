package sir.timekeep.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "MEMO_GROUP")
public class Group extends AbstractEntity {
    @Basic(optional = false)
    @Column(name="name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "groups")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User groupCreator;

    @OneToMany(mappedBy = "group")
    private List<Post> posts;

    // getters
    public String getName() {return name;}

    public List<User> getUsers() {return users;}

    public User getGroupCreator() {return groupCreator;}

    public List<Post> getPosts() {return posts;}

    // setters
    public void setName(String name) {this.name = name;}

    public void setUsers(List<User> users) {this.users = users;}

    public void setGroupCreator(User groupCreator) {this.groupCreator = groupCreator;}

    public void setPosts(List<Post> posts) {this.posts = posts;}

    // extra
    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", groupCreator=" + groupCreator +
                '}';
    }
}
