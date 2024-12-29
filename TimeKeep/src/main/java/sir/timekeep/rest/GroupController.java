package sir.timekeep.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sir.timekeep.model.Group;
import sir.timekeep.model.User;
import sir.timekeep.service.GroupService;
import org.springframework.http.ResponseEntity;
import sir.timekeep.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GroupController {
    private static final Logger LOG = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;
    private final UserService userService;

    public GroupController(GroupService groupService, UserService userService){
        this.groupService = groupService;
        this.userService = userService;
    }

    // TO DO: Authorization based on user login
    @GetMapping(value ="/{id}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Group>> findByCreator(@PathVariable Integer id){
        Optional<List<Group>> groups = groupService.findAllByCreator(id);
        return groups.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // TO DO: Authorization based on user login implementation
    @PreAuthorize("hasRole('ROLE_PREMIMUM')")
    @PostMapping(value = "/{id}/groups", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGroup(@PathVariable Integer id, @RequestBody Group group){
        Objects.requireNonNull(group);
        userService.createGroup(userService.find(id), group);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TO DO: Authorization based on user login implementation
    @PreAuthorize("hasRole('ROLE_PREMIMUM') && user_id == groupService.find(group_id).get().getGroupCreator()")
    @PutMapping(value = "/{id}/groups/{id_group}/add_user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUserToGroup(@PathVariable Integer user_id, @PathVariable Integer group_id, @RequestBody User user){
        Objects.requireNonNull(user);
        if (!groupService.exists(group_id) || userService.find(user_id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.addUserToGroup(userService.find(user_id), groupService.find(group_id), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TO DO: Authorization based on user login implementation
    @PreAuthorize("hasRole('ROLE_PREMIMUM') && user_id == groupService.find(group_id).get().getGroupCreator()")
    @PutMapping(value = "/{id}/groups/{id_group}/remove_user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removeUserFromGroup(@PathVariable Integer user_id, @PathVariable Integer group_id, @RequestBody User user){
        Objects.requireNonNull(user);
        if (!groupService.exists(group_id) || userService.find(user_id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.removeUserFromGroup(userService.find(user_id), groupService.find(group_id), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TO DO: Authorization based on user login implementation
    @PreAuthorize("(!#groupService.find(id_group).get().groupCreator() == id)")
    @DeleteMapping(value = "/{id}/groups/{id_group}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Integer id, @PathVariable Integer id_group){
        return groupService.find(id_group)
                .map(group -> {
                    groupService.remove(group);
                    return new ResponseEntity<Void>(ResponseEntity.ok().build().getStatusCode());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
        // Not sure if the simplified version isn't worse to read and understand...
        /*
        Optional<Group> group = groupService.find(id_group);
        if (group.isPresent()){
            groupService.remove(group.get());
            return new ResponseEntity<>(ResponseEntity.ok().build().getStatusCode());
        } else{
            return new ResponseEntity<>(ResponseEntity.notFound().build().getStatusCode());
        }
         */
    }
}
