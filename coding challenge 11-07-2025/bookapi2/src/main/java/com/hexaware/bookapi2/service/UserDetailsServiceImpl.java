package com.hexaware.bookapi2.service;

import com.hexaware.bookapi2.model.User;
import com.hexaware.bookapi2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Looking for user: " + username);

        Optional<User> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            System.out.println("❌ User not found in DB: " + username);
            throw new UsernameNotFoundException("User not found");
        }

        User user = optionalUser.get();
        System.out.println("✅ User found: " + user.getUsername());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList() // no roles right now
        );
    }

    // Register user if you want to expose this method
    public User register(User user) {
        System.out.println("📝 Registering new user: " + user.getUsername());
        return userRepo.save(user);
    }
}
