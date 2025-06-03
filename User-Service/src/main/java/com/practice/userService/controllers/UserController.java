package com.practice.userService.controllers;

import com.practice.userService.dtos.UserDTO;
import com.practice.userService.models.AppUser;
import com.practice.userService.service.UserMapperService;
import com.practice.userService.serviceImpl.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final AppUserService userService;
    private final UserMapperService userMapperService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        try {
            AppUser appUser = userService.getUserById(id);
            return ResponseEntity.ok(userMapperService.mapToUserDTO(appUser));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getUsers() {
        try {
            return ResponseEntity.ok( userService.getAllUsers()
                    .stream()
                    .map(userMapperService::mapToUserDTO)
                    .collect(Collectors.toList()));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated User: " + auth.getName());
        auth.getAuthorities().forEach(a -> System.out.println("Authority: " + a.getAuthority()));

        try {
            AppUser appUser = userMapperService.mapToAppUser(userDTO);
            userService.createUser(appUser);
            return ResponseEntity.ok(userMapperService.mapToUserDTO(appUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        try {
            AppUser appUser = userMapperService.mapToAppUser(userDTO);
            userService.updateUser(appUser);
            return ResponseEntity.ok(userMapperService.mapToUserDTO(appUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
