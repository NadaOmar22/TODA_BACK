package com.practice.userService.controllers;

import com.practice.userService.dtos.OTPRequest;
import com.practice.userService.models.AppUser;
import com.practice.userService.service.AuthService;
import com.practice.userService.service.OTPService;
import com.practice.userService.service.PasswordService;
import com.practice.userService.serviceImpl.AppUserService;
import com.practice.userService.dtos.AuthRequest;
import com.practice.userService.dtos.AuthResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.practice.userService.dtos.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AppUserService userDetailsService;
    private final AuthService authService;
    private final OTPService otpService;
    private final PasswordService passwordService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
        System.out.println("login : 2");
        String token = authService.login(authRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) throws BadRequestException {
        AppUser appUser = authService.register(request);
        return ResponseEntity.ok(appUser);
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<?> verifyOTP(@RequestBody OTPRequest request) {
        otpService.confirmOTP(request.getOtp());
        userDetailsService.enableUser(request.getOtp());
        return ResponseEntity.ok(HttpEntity.EMPTY);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody AuthRequest authRequest) {
        passwordService.forgetPassword(authRequest.getUserName());
        return ResponseEntity.ok(HttpEntity.EMPTY);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody AuthRequest authRequest) throws BadRequestException {
        passwordService.resetPassword(authRequest.getUserName(), authRequest.getPassword(), authRequest.getConfirmationPassword());
        return ResponseEntity.ok(HttpEntity.EMPTY);
    }
}

