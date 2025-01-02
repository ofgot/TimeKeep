package sir.timekeep.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMO_POST")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "POST_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Post extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @Column(name = "timeOfCreation", nullable = false)
    private LocalDateTime timeOfCreation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User postCreator;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    private List<Media> media;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    // constructors

    public Post(String name, String description, User postCreator, List<Media> media, Group group) {
        this.name = name;
        this.description = description;
        this.timeOfCreation = LocalDateTime.now();
        this.postCreator = postCreator;
        this.media = media;
        this.group = group;
    }

    public Post() {

    }

    // getters
    public String getName() {
        return name;
    }

    public String getDescription(){return description;}

    public LocalDateTime getDateOfCreation() {
        return timeOfCreation;
    }

    public User getPostCreator() {
        return postCreator;
    }

    public Group getGroup() {
        return group;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description){this.description = description;}

    public void setDateOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public void setPostCreator(User postCreator) {
        this.postCreator = postCreator;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    // extra

    public void addMedia(Media media) {
        if (this.media == null) {
            this.media = new ArrayList<>();
        }
        this.media.add(media);
    }

    @Override
    public String toString() {
        return "Post{" +
                "name='" + name + '\'' +
                ", postCreator=" + postCreator +
                '}';
    }
}
