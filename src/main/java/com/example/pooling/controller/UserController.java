package com.example.pooling.controller;

import com.example.pooling.dto.LoginRequestDTO;
import com.example.pooling.dto.LoginResponseDTO;
import com.example.pooling.dto.UserRequestDTO;
import com.example.pooling.dto.UserResponseDTO;
import com.example.pooling.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ 1️⃣ PROFESSIONAL REGISTER API
    @PostMapping("/register")
    public UserResponseDTO registerUser(
            @Valid @RequestBody UserRequestDTO dto) {

        log.info("Registering user: {}", dto.getEmail());
        return userService.saveUser(dto);
    }

    // ✅ 2️⃣ KEEP OLD API (Backward Compatibility)
    @PostMapping
    public UserResponseDTO createUser(
            @Valid @RequestBody UserRequestDTO dto) {

        log.warn("Using deprecated /users endpoint for registration");
        return userService.saveUser(dto);
    }

    // ✅ 3️⃣ GET ALL USERS (ADMIN)
    @GetMapping
    public List<UserResponseDTO> getUsers() {
        log.info("Fetching all users");
        return userService.getAllUsers();
    }

    // ✅ 4️⃣ LOGIN API
    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO dto) {

        log.info("Login attempt for email: {}", dto.getEmail());
        return userService.login(dto);
    }
}
