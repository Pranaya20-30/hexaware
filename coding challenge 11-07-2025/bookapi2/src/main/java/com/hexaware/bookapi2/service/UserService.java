package com.hexaware.bookapi2.service;

import com.hexaware.bookapi2.dto.AuthRequest;
import com.hexaware.bookapi2.dto.AuthResponse;
import com.hexaware.bookapi2.model.User;

public interface UserService {
    User register(User user);
    AuthResponse login(AuthRequest request);
}
