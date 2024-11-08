package sir.timekeep.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "MEMO_USER")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "surname", nullable = false)
    private String surname;

    @Basic(optional = false)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Basic(optional = false)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Basic(optional = false)
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

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

    public User() {}

    public User(String name, String surname, String username, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = Role.USUAL;
    }

    // getters
    public String getName() {return name;}

    public String getSurname() {return surname;}

    public String getUsername() {return username;}

    public String getEmail() {return email;}

    public String getPassword() {return password;}

    public Role getRole() {return role;}

    public List<Group> getGroups() {return groups;}

    public List<Group> getCreatedGroups() {return createdGroups;}

    public List<Post> getPosts() {return posts;}

    // setters
    public void setName(String name) {this.name = name;}

    public void setSurname(String surname) {this.surname = surname;}

    public void setUsername(String userName) {this.username = userName;}

    public void setEmail(String email) {this.email = email;}

    public void setPassword(String password) {this.password = password;}

    public void setRole(Role role) {this.role = role;}

    public void setGroups(List<Group> groups) {this.groups = groups;}

    public void setCreatedGroups(List<Group> createdGroups) {this.createdGroups = createdGroups;}

    public void setPosts(List<Post> posts) {this.posts = posts;}

    // extra
    public void erasePassword() {
        this.password = null;
    }

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public boolean isPremium(){return role == Role.PREMIUM;}

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userName='" + username + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                '}';
    }
}
