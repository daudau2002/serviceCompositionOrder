package com.server.userservice.converter;

import com.server.userservice.dto.UserDTO;
import com.server.userservice.modal.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getStatus());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setStatus(userDTO.getStatus());
        user.setPhone(userDTO.getPhone());
        return user;
    }

    public User convertToEntity(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(hashPassword(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setStatus(userDTO.getStatus());
        user.setPhone(userDTO.getPhone());
        return user;
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
