package com.practice.userService.serviceImpl;

import com.practice.userService.dtos.UserDTO;
import com.practice.userService.models.AppUser;
import com.practice.userService.service.UserMapperService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapperServiceImpl implements UserMapperService {

    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO mapToUserDTO(AppUser appUser){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(appUser.getId());
        userDTO.setUserName(appUser.getUsername());
        userDTO.setName(appUser.getName());
        userDTO.setRole(appUser.getRole());
        return userDTO;
    }
    @Override
    public AppUser mapToAppUser(UserDTO userDTO){
        AppUser appUser = new AppUser();
        appUser.setId(userDTO.getId());
        appUser.setUserName(userDTO.getUserName());
        appUser.setRole(userDTO.getRole());
        appUser.setName(userDTO.getName());
        appUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return appUser;
    }
}
