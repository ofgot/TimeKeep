package sir.timekeep.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table (name = "MEMO_CAPSULE")
@DiscriminatorValue("Capsule")
public class Capsule extends Post{

    @Basic(optional = false)
    @Column(name="dateOfOpening", nullable = false)
    private Date dateOfOpening;

    @Basic(optional = false)
    @Column(name="open", nullable = false)
    private boolean open;

    // getters
    public Date getDateOfOpening() {return dateOfOpening;}

    public boolean isOpen() {return open;}

    // setters
    public void setDateOfOpening(Date dateOfOpening) {this.dateOfOpening = dateOfOpening;}

    public void setOpen(boolean open) {this.open = open;}

    //extra
    @Override
    public String toString() {
        return "Capsule{" +
                "dateOfOpening=" + dateOfOpening +
                '}';
    }
}
