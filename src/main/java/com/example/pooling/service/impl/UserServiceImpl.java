package com.example.pooling.service.impl;

import com.example.pooling.dto.LoginRequestDTO;
import com.example.pooling.dto.LoginResponseDTO;
import com.example.pooling.dto.UserRequestDTO;
import com.example.pooling.dto.UserResponseDTO;
import com.example.pooling.entity.User;
import com.example.pooling.exception.DuplicateEmailException;
import com.example.pooling.exception.InvalidLoginException;
import com.example.pooling.repository.UserRepository;
import com.example.pooling.security.JwtUtil;
import com.example.pooling.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ===================== SIGNUP =====================
    @Override
    public UserResponseDTO saveUser(UserRequestDTO dto) {

        userRepository.findByEmail(dto.getEmail())
                .ifPresent(u -> {
                    throw new DuplicateEmailException(
                            "Email already exists: " + dto.getEmail()
                    );
                });

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        // 🔐 PASSWORD ENCRYPTION
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    // ===================== GET ALL USERS =====================
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ===================== LOGIN (STEP-4 COMPLETE) =====================
    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new InvalidLoginException("Invalid email or password")
                );

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidLoginException("Invalid email or password");
        }

        // 🔐 JWT TOKEN GENERATE (THIS WAS MISSING)
        String token = jwtUtil.generateToken(user.getId());

        return LoginResponseDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)          // 🔥 VERY IMPORTANT
                .message("Login successful")
                .build();
    }

    // ===================== MAPPER =====================
    private UserResponseDTO mapToResponse(User user) {
        UserResponseDTO res = new UserResponseDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setPhone(user.getPhone());
        return res;
    }
}
