package com.example.pooling.service;

import com.example.pooling.dto.LoginRequestDTO;
import com.example.pooling.dto.LoginResponseDTO;
import com.example.pooling.dto.UserRequestDTO;
import com.example.pooling.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO saveUser(UserRequestDTO dto);
    List<UserResponseDTO> getAllUsers();
    LoginResponseDTO login(LoginRequestDTO dto);

}
