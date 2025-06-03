package com.practice.userService.service;

import com.practice.userService.dtos.AuthRequest;
import com.practice.userService.dtos.RegistrationRequest;
import com.practice.userService.models.AppUser;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String login(AuthRequest authRequest) throws Exception;
    AppUser register(RegistrationRequest request) throws BadRequestException;
    Boolean isValidEmail(String userName);
    Boolean isValidPassword(String password);
}
