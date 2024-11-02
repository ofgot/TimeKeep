package sir.timekeep.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "MEMO_CAPSULE")
@DiscriminatorValue("Capsule")
public class Capsule extends Post{

    @Basic(optional = false)
    @Column(name="timeOfOpening", nullable = false)
    private LocalDateTime timeOfOpening;

    @Basic(optional = false)
    @Column(name="open", nullable = false)
    private boolean open;

    // getters
    public LocalDateTime getDateOfOpening() {return timeOfOpening;}

    public boolean isOpen() {return open;}

    // setters
    public void setDateOfOpening(LocalDateTime timeOfOpening) {this.timeOfOpening = timeOfOpening;}

    public void setOpen(boolean open) {this.open = open;}

    //extra
    @Override
    public String toString() {
        return "Capsule{" +
                "timeOfOpening=" + timeOfOpening +
                '}';
    }
}
