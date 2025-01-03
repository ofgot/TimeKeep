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

@RestController
@RequestMapping("/rest")
public class GroupController {
    private static final Logger LOG = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;
    private final UserService userService;

    public GroupController(GroupService groupService, UserService userService){
        this.groupService = groupService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('PREMIUM') && (#id == authentication.principal.user.id)")
    @GetMapping(value ="/{id}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Group>> findByCreator(@PathVariable Integer id){
        Optional<List<Group>> groups = groupService.findAllByCreator(id);
        return groups.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('PREMIUM') && (#id == authentication.principal.user.id)")
    @PostMapping(value = "/{id}/groups", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGroup(@PathVariable Integer id, @RequestBody Group group){
        Objects.requireNonNull(group);
        userService.createGroup(userService.find(id).get(), group);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('PREMIUM') && (#user_id == authentication.principal.user.id)")
    @PutMapping(value = "/{user_id}/groups/{group_id}/add_user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUserToGroup(@PathVariable Integer user_id, @PathVariable Integer group_id, @RequestBody User user){
        Objects.requireNonNull(user);
        if (!groupService.exists(group_id) || userService.find(user_id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(user_id, groupService.find(group_id).get().getGroupCreator().getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (groupService.find(group_id).get().getUsers().contains(user)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.addUserToGroup(userService.find(user_id).get(), groupService.find(group_id).get(), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PREMIUM') && (#user_id == authentication.principal.user.id)")
    @PutMapping(value = "/{user_id}/groups/{group_id}/remove_user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removeUserFromGroup(@PathVariable Integer user_id, @PathVariable Integer group_id, @RequestBody User user){
        Objects.requireNonNull(user);
        if (!groupService.exists(group_id) || userService.find(user_id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(user_id, groupService.find(group_id).get().getGroupCreator().getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (!groupService.find(group_id).get().getUsers().contains(user)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.removeUserFromGroup(userService.find(user_id).get(), groupService.find(group_id).get(), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PREMIUM') && (#user_id == authentication.principal.user.id)")
    @DeleteMapping(value = "/{user_id}/groups/{group_id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Integer user_id, @PathVariable Integer group_id){
        Optional<Group> group = groupService.find(group_id);
        if (group.isPresent()){
            if (!Objects.equals(user_id, group.get().getGroupCreator().getId())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            groupService.remove(group.get());
            return new ResponseEntity<>(ResponseEntity.ok().build().getStatusCode());
        } else{
            return new ResponseEntity<>(ResponseEntity.notFound().build().getStatusCode());
        }
    }
}
