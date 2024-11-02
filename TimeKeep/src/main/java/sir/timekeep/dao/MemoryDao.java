package sir.timekeep.dao;

import org.springframework.stereotype.Repository;
import sir.timekeep.model.Memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryDao extends BaseDao<Memory> {
    public MemoryDao() {super(Memory.class);}
}
