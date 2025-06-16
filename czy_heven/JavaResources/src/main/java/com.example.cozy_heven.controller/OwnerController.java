package com.example.cozy_heven.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.cozy_heven.entity.*;

@Controller
public class OwnerController {

    @Autowired
    private UserRepo repo;

    @GetMapping("/owner")
    public String welcomePage() {
        return "index.jsp";
    }

    @GetMapping("/owner/register")
    public String getUser(@RequestParam int userId, Model model) {
        model.addAttribute("updateUser", repo.findById(userId).orElse(null));
        model.addAttribute("userList", repo.findAll());
        return "owner.jsp";
    }

  
    @PostMapping("/owner/register")
    public String register(@ModelAttribute Users user, Model model) {
        Users existingUser = repo.findByEmail(user.getEmail());

        if (existingUser != null) {
            model.addAttribute("error", "Email already exists. Please log in.");
            return "ownerlogin.jsp";
        }

        if (user.getCreatedAt() == null) {
            user.setCreatedAt(Timestamp.from(Instant.now()));
        }

        repo.save(user);

        model.addAttribute("email", user.getEmail());
        return "ownerlogin.jsp";
    }

    @PostMapping("/owner/login")
    public String login(@RequestParam String email,
                        @RequestParam String passwordHash,
                        Model model) {
        Users user = repo.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "Email not found. Please register first.");
            model.addAttribute("showRegister", true);
            return "ownerlogin.jsp";
        }

        if (!user.getPasswordHash().equals(passwordHash)) {
            model.addAttribute("error", "Incorrect password.");
            return "ownerlogin.jsp";
        }

        model.addAttribute("name", user.getName());
        return "home.jsp";
    }
    @PostMapping("/owner/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {
        Users user = repo.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "forgotpassword.jsp";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("email", email);
            return "forgotpassword.jsp";
        }

        if (newPassword.equals(user.getPasswordHash())) {
            model.addAttribute("error", "New password cannot be the same as the previous password.");
            model.addAttribute("email", email);
            return "forgotpassword.jsp";
        }

        user.setPasswordHash(newPassword); // In production, hash it
        repo.save(user);

        model.addAttribute("message", "Password has been reset successfully.");
        return "ownerlogin.jsp";
    }
}


