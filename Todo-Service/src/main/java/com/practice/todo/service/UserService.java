package com.practice.todo.service;

import com.practice.todo.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(String userId);
}
