package sir.timekeep.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "GROUP")
public class Group extends AbstractEntity {
    @Basic(optional = false)
    @Column(name="name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name="firstname", nullable = false)
    private String firstName;

    @ManyToMany(mappedBy = "groups")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User groupCreator;

    @OneToMany(mappedBy = "group")
    private List<Post> posts;

}
