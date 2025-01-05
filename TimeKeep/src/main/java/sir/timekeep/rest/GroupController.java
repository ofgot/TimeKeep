package sir.timekeep.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sir.timekeep.exception.AlreadyTakenException;
import sir.timekeep.model.Group;
import sir.timekeep.model.User;
import sir.timekeep.responseClasses.ResponseGroup;
import sir.timekeep.service.GroupService;
import org.springframework.http.ResponseEntity;
import sir.timekeep.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @PreAuthorize("!anonymous && (hasRole('USUAL') || hasRole('PREMIUM'))")
    @GetMapping(value ="/{id}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseGroup>> findUsersGroups(@PathVariable Integer id){
        if (userService.find(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.find(id).get();
        List<Group> groups = user.getGroups();
        List<ResponseGroup> responseGroups = new ArrayList<>();
        for (Group group : groups){
            ResponseGroup tmpGroup = new ResponseGroup(group);
            responseGroups.add(tmpGroup);
        }
        System.out.println(responseGroups);
        return ResponseEntity.ok(responseGroups);
    }

    @PreAuthorize("!anonymous && hasRole('PREMIUM')")
    @PostMapping(value = "/{id}/groups", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGroup(@PathVariable Integer id, @RequestBody Group group){
        Objects.requireNonNull(group);
        userService.createGroup(userService.find(id).get(), group);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("!anonymous && hasRole('PREMIUM')")
    @PutMapping(value = "/{user_id}/groups/{group_id}/add_user/{add_id}")
    public ResponseEntity<Void> addUserToGroup(@PathVariable Integer user_id, @PathVariable Integer group_id, @PathVariable Integer add_id){
        if (!groupService.exists(group_id) || userService.find(user_id).isEmpty() || userService.find(add_id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(user_id, groupService.find(group_id).get().getGroupCreator().getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.addUserToGroup(userService.find(user_id).get(), groupService.find(group_id).get(), userService.find(add_id).get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("!anonymous && hasRole('PREMIUM')")
    @PutMapping(value = "/{user_id}/groups/{group_id}/remove_user/{remove_id}")
    public ResponseEntity<Void> removeUserFromGroup(@PathVariable Integer user_id, @PathVariable Integer group_id, @PathVariable Integer remove_id){
        if (!groupService.exists(group_id) || userService.find(user_id).isEmpty() || userService.find(remove_id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(user_id, groupService.find(group_id).get().getGroupCreator().getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.removeUserFromGroup(userService.find(user_id).get(), groupService.find(group_id).get(), userService.find(remove_id).get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void deleteAllMembersGroup(int user_id, Group group){
        if (userService.findAll().isEmpty() || userService.find(user_id).isEmpty()){
            return;
        }
        User creator = userService.find(user_id).get();
        List<User> users = userService.findAll().get();
        for (User user : users){
            if (!user.equals(creator)){
                userService.removeUserFromGroup(creator, group, user);
            }
        }
        userService.removeUserFromGroup(creator, group, creator);
    }

    @PreAuthorize("!anonymous && hasRole('PREMIUM')")
    @DeleteMapping(value = "/{user_id}/groups/{group_id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Integer user_id, @PathVariable Integer group_id){
        if (groupService.find(group_id).isEmpty()){
            return new ResponseEntity<>(ResponseEntity.notFound().build().getStatusCode());
        }
        Group group = groupService.find(group_id).get();
        if (!Objects.equals(user_id, group.getGroupCreator().getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        deleteAllMembersGroup(user_id, group);
        groupService.remove(group);
        return new ResponseEntity<>(ResponseEntity.ok().build().getStatusCode());
    }
}
