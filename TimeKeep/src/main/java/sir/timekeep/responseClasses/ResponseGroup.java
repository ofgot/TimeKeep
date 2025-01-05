package sir.timekeep.responseClasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import sir.timekeep.model.AbstractEntity;
import sir.timekeep.model.Group;
import java.util.List;

public class ResponseGroup extends AbstractEntity {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("groupCreator")
    private int groupCreator;
    @JsonProperty("users")
    private List<Integer> users;

    public ResponseGroup(Group group){
        this.id = group.getId();
        this.name = group.getName();
        this.groupCreator = group.getGroupCreator().getId();
        this.users = group.getUsers().stream().map(tmpUser -> tmpUser.getId()).toList();
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", groupCreator=" + groupCreator +
                ", users " + users +
                '}';
    }
}
