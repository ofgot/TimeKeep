package sir.timekeep.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table (name = "MEMO_CAPSULE")
@DiscriminatorValue("Capsule")
public class Capsule extends Post{

    @Basic(optional = false)
    @Column(name="timeOfOpening", nullable = false)
    private LocalDateTime timeOfOpening;

    @Basic(optional = false)
    @Column(name="isOpen", nullable = false)
    private boolean isOpen;

    // constructors
    public Capsule(){}

    public Capsule(String name, String description, List<Media> media, User postCreator, Group group, LocalDateTime timeOfOpening) {
        super(name, description, postCreator, media, group);
        this.timeOfOpening = timeOfOpening;
        this.isOpen = false;
    }

    //getters
    public boolean isOpen() {
        if (timeOfOpening.isBefore(LocalDateTime.now())) {
            isOpen = true;
        }
        return isOpen;
    }
    //extra
    @Override
    public String toString() {
        return "Capsule{" +
                "timeOfOpening=" + timeOfOpening +
                '}';
    }
}
