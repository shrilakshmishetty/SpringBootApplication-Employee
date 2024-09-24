package com.example.JpaNewAssignment.ServiceImpl;

import com.example.JpaNewAssignment.Model.Users;
import com.example.JpaNewAssignment.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.secret}")
    private String secretKey;





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    // Method to save user to the database
    public void saveUser(String username, String password, String role) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encrypt the password
        user.setRole(role);
        userRepository.save(user);
    }
    public String authenticateAndGenerateToken(String username, String password) throws Exception {
        UserDetails userDetails = loadUserByUsername(username);


        System.out.println("Stored Encoded Password: " + userDetails.getPassword());
        System.out.println("Provided Password: " + password);


        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("Password matches, generating token");
            return generateToken(userDetails);
        } else {
            System.out.println("Password does not match");
            throw new Exception("Invalid credentials");
        }
    }

    private String  generateToken(UserDetails userDetails) {
        System.out.println("Generating token for user: " + userDetails.getUsername());
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
        System.out.println("Generated Token: " + token);
        return token;

    }
}
