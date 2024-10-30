package sir.timekeep.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "POST")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "POST_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Post extends AbstractEntity {

    @Basic(optional = false)
    @Column(name="name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name="text")
    private String text;

    @Basic(optional = false)
    @Column(name="record")
    private String record;

    @Basic(optional = false)
    @Column(name="video")
    private String video;

    @Basic(optional = false)
    @Column(name="dateOfCreation", nullable = false)
    private Date dateOfCreation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User postCreator;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
