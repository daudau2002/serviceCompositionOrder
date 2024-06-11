package com.server.userservice.service;

import com.server.userservice.dto.UserDTO;

import java.util.List;

public interface IUserService {
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(Long id);
    public UserDTO saveUser(UserDTO user);
    public UserDTO updateUser(UserDTO user);
    public void deleteUser(Long id);
}
