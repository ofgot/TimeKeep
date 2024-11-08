package sir.timekeep.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MEMO_POST")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "POST_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Post extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "type")
    private PostType type;

    //@Basic(optional = false)
    @Column(name = "text")
    private String text;

    //@Basic(optional = false)
    @Column(name = "record")
    private String record;

    //@Basic(optional = false)
    @Column(name = "photo")
    private String photo;

    //@Basic(optional = false)
    @Column(name = "video")
    private String video;

    @Basic(optional = false)
    @Column(name = "timeOfCreation", nullable = false)
    private LocalDateTime timeOfCreation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User postCreator;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    // constructors
    public Post(){}

    protected Post(String name, String link, PostType postType){
        this.name = name;
        this.timeOfCreation = LocalDateTime.now();
        switch(postType) {
            case TEXT -> this.text = link;
            case RECORD -> this.record = link;
            case PHOTO -> this.photo = link;
            case VIDEO -> this.video = link;
        }
        this.type = postType;
    }

    // getters
    public String getName() {return name;}

    public String getText() {return text;}

    public String getRecord() {return record;}

    public String getVideo() {return video;}

    public String getPhoto() {return photo;}

    public LocalDateTime getDateOfCreation() {return timeOfCreation;}

    public User getPostCreator() {return postCreator;}

    public Group getGroup() {return group;}

    // setters
    public void setName(String name) {this.name = name;}

    public void setText(String text) {this.text = text;}

    public void setRecord(String record) {this.record = record;}

    public void setPhoto(String photo) {this.photo = photo;}

    public void setVideo(String video) {this.video = video;}

    public void setDateOfCreation(LocalDateTime timeOfCreation) {this.timeOfCreation = timeOfCreation;}

    public void setPostCreator(User postCreator) {this.postCreator = postCreator;}

    public void setGroup(Group group) {this.group = group;}

    // extra
    @Override
    public String toString() {
        return "Post{" +
                "name='" + name + '\'' +
                ", postCreator=" + postCreator +
                '}';
    }
}
