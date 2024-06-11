package com.server.userservice.service.impl;

import com.server.userservice.converter.UserConverter;
import com.server.userservice.dto.UserDTO;
import com.server.userservice.modal.User;
import com.server.userservice.repository.UserRepository;
import com.server.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? userConverter.convertToDTO(user) : null;
    }

    @Override
    public UserDTO saveUser(UserDTO userDto) {
        User user = userConverter.convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return userConverter.convertToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDto) {
        User user = userRepository.findById(userDto.getId()).orElse(null);
        if (user != null) {
            userConverter.convertToEntity(userDto, user);
            User updatedUser = userRepository.save(user);
            return userConverter.convertToDTO(updatedUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
