package sir.timekeep.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "MEMORY")
@DiscriminatorValue("Memory")
public class Memory extends Post {

}