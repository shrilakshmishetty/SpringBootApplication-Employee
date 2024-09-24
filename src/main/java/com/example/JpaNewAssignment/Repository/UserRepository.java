package com.example.JpaNewAssignment.Repository;

import com.example.JpaNewAssignment.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByUsername(String username);
}
