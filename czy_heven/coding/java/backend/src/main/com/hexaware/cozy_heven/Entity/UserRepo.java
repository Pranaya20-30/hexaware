package com.example.cozy_heven.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cozy_heven.entity.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {
	Users findByEmail(String email);// You can add custom queries if needed
}
