package sir.timekeep.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sir.timekeep.rest.util.RestUtils;
import sir.timekeep.model.User;
import sir.timekeep.security.model.UserDetails;
import sir.timekeep.service.UserService;

@RestController
@RequestMapping("/rest")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerUser(@RequestBody User user) {
        userService.persist(user);
        LOG.debug("User {} successfully registered.", user);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrent(Authentication auth) {
        assert auth.getPrincipal() instanceof UserDetails;
        return ((UserDetails) auth.getPrincipal()).getUser();
    }

    @PreAuthorize("hasRole('USUAL') && !anonymous")
    @PutMapping("/users/{id}/changeRole")
    public ResponseEntity<Void> changeUserRole(@PathVariable Integer id) {
        userService.changeUserToPremium(id);
        return ResponseEntity.ok().build();
    }

}
