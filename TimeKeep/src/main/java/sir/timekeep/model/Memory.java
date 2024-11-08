package sir.timekeep.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table (name = "MEMO_MEMORY")
@DiscriminatorValue("Memory")
@NamedQueries({
        @NamedQuery(name = "Memory.listByPostCreatorId", query = "SELECT m FROM Memory m WHERE m.postCreator = :user_id")
})
public class Memory extends Post {
    public Memory(){}

    public Memory(String name, List<Media> media, LocalDateTime timeOfCreation, User postCreator, Group group) {
        super(name, timeOfCreation, postCreator, media, group);
    }
}
