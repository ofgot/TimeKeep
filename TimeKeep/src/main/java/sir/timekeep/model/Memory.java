package sir.timekeep.model;

import jakarta.persistence.*;

@Entity
@Table (name = "MEMO_MEMORY")
@DiscriminatorValue("Memory")
@NamedQueries({
        @NamedQuery(name = "Memory.listByPostCreatorId", query = "SELECT m FROM Memory m WHERE m.postCreator = :user_id")
})
public class Memory extends Post {
}
