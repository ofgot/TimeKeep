package sir.timekeep.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table (name = "MEMO_CAPSULE")
@DiscriminatorValue("Capsule")
public class Capsule extends Post{

    @Basic(optional = false)
    @Column(name="dateofopening", nullable = false)
    private Date dateOfOpening;

    @Basic(optional = false)
    @Column(name="open", nullable = false)
    private boolean open;

}
