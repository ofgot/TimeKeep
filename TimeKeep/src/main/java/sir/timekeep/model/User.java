package sir.timekeep.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "USER")
public class User extends AbstractEntity{

    @Basic(optional = false)
    @Column(name="name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name="surname", nullable = false)
    private String surname;

    @Basic(optional = false)
    @Column(name="username", nullable = false)
    private String userName;

    @Basic(optional = false)
    @Column(name="password", nullable = false)
    private String password;

    @Basic(optional = false)
    @Column(name="ispremium", nullable = false)
    private boolean isPremium;

    @ManyToMany
    @OrderBy("name")
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    @OneToMany(mappedBy = "groupCreator")
    private List<Group> createdGroups;

    @OneToMany(mappedBy = "postCreator")
    private List<Post> posts;
}
