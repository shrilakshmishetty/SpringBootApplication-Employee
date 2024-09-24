package com.example.JpaNewAssignment.Controller;

import com.example.JpaNewAssignment.Model.Users;
import com.example.JpaNewAssignment.ServiceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder=passwordEncoder;
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users users) {
        System.out.println(users.getUsername()+" "+users.getPassword()+" "+users.getRole());
        userService.saveUser(
               users.getUsername(),
                users.getPassword(),
                users.getRole()
        );
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Users users) {
        try {
            System.out.println(users.getUsername()+" + "+users.getPassword());
            String token = userService.authenticateAndGenerateToken(users.getUsername(), users.getPassword());
            return ResponseEntity.ok("Bearer " + token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    }


