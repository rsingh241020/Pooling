package com.example.pooling.controller;

import com.example.pooling.dto.LoginRequestDTO;
import com.example.pooling.dto.LoginResponseDTO;
import com.example.pooling.dto.UserRequestDTO;
import com.example.pooling.dto.UserResponseDTO;
import com.example.pooling.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(
            @Valid @RequestBody UserRequestDTO dto) {
        return userService.saveUser(dto);
    }

    @GetMapping
    public List<UserResponseDTO> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO dto) {
        return userService.login(dto);
    }
}
