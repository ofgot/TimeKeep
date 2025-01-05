package sir.timekeep.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table (name = "MEMO_CAPSULE")
@DiscriminatorValue("Capsule")
public class Capsule extends Post{

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @Basic(optional = false)
    @Column(name="timeOfOpening", nullable = false)
    private LocalDateTime timeOfOpening;

    @Basic(optional = false)
    @Column(name="isOpen", nullable = false)
    private boolean isOpen;

    // constructors
    public Capsule(){
        super();
        this.setDateOfCreation(LocalDateTime.now());
    }

    public Capsule(String name, String description, List<Media> media, User postCreator, Group group, LocalDateTime timeOfOpening) {
        super(name, description, postCreator, media, group);
        this.timeOfOpening = timeOfOpening;
        this.isOpen = !this.timeOfOpening.isAfter(LocalDateTime.now());
    }

    //getters
    public LocalDateTime getTimeOfOpening() {
        return timeOfOpening;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setTimeOfOpening(LocalDateTime timeOfOpening) {
        this.timeOfOpening = timeOfOpening;
    }

    // This the only time, when we get to isOpen
    public boolean getIsOpen() {
        this.isOpen = !this.timeOfOpening.isAfter(LocalDateTime.now());
        return isOpen;
    }

    // I tried Scheduled, but it didn't work, because cron apparently cannot take variables, that are not known during compilation
    /*
    public String getCronExpressionFromTime(LocalDateTime time) {
        return String.format("0 %d %d %d * * *", time.getMinute(), time.getHour(), time.getDayOfMonth());
    }
    @Scheduled(cron = "#{T(sir.timekeep.model.Capsule).getCronExpressionFromTime('(sir.timekeep.model.Capsule).timeOfOpening')}")
    public void setIsOpen(){
        this.isOpen = true;
    }
     */

    //extra
    @Override
    public String toString() {
        return "Capsule{" +
                "timeOfOpening=" + timeOfOpening +
                '}';
    }
}
