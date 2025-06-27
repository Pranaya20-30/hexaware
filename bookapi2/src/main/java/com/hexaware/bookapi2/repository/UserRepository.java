package com.hexaware.bookapi2.repository;

import com.hexaware.bookapi2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}