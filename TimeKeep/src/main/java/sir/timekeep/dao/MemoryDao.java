package sir.timekeep.dao;

import org.springframework.stereotype.Repository;
import sir.timekeep.model.Memory;

@Repository
public class MemoryDao extends PostMemoryCapsuleDao<Memory> {
    public MemoryDao() {super(Memory.class);}
}
