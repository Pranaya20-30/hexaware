package com.hexaware.bookapi2.repository;

import com.hexaware.bookapi2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // For manual login (e.g., in your AuthService or UserService)
    Optional<User> findByUsernameAndPassword(String username, String password);

    // For Spring Security (load user by username)
    Optional<User> findByUsername(String username);
}
