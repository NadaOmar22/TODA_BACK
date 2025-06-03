package com.practice.userService.service;

import com.practice.userService.dtos.UserDTO;
import com.practice.userService.models.AppUser;
import org.springframework.stereotype.Service;

@Service
public interface UserMapperService {
    UserDTO mapToUserDTO(AppUser appUser);
    AppUser mapToAppUser(UserDTO userDTO);
}
