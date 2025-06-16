package com.example.cozy_heven;

import com.example.cozy_heven.entity.Users;
import com.example.cozy_heven.entity.Users.Role;
import com.example.cozy_heven.entity.UserRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CozyHevenApplicationTests {

    @Autowired
    private UserRepo userRepo;

    private Users testUser;

    @BeforeEach
    void setup() {
        testUser = new Users();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("password123");
        testUser.setRole(Role.guest);
        testUser.setContactNumber("9876543210");
        testUser.setAddress("Test Address");
        testUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        userRepo.save(testUser);
    }

    @Test
    void testUserIsSaved() {
        Users found = userRepo.findById(testUser.getUserId()).orElse(null);
        assertNotNull(found);
        assertEquals("Test User", found.getName());
    }



    @Test
    void testFindByEmail() {
        Users user = userRepo.findByEmail("test@example.com");
        assertNotNull(user);
        assertEquals("Test User", user.getName());
    }



    @Test
    void testAllArgsConstructorEquivalent() {
        Users user = new Users();
        user.setUserId(100);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPasswordHash("secret123");
        user.setContactNumber("9876543210");
        user.setAddress("Chennai");
        user.setRole(Role.guest);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        assertEquals(100, user.getUserId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("secret123", user.getPasswordHash());
        assertEquals("9876543210", user.getContactNumber());
        assertEquals("Chennai", user.getAddress());
        assertEquals(Role.guest, user.getRole());
    }

   

    @AfterEach
    void cleanup() {
        userRepo.deleteAll();
    }
}
