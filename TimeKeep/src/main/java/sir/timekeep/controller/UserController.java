package sir.timekeep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sir.timekeep.model.User;
import sir.timekeep.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody User user) {
        System.out.println("Received user: " + user);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.persist(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
