package com.practice.userService.service;

import com.practice.userService.models.OTP;
import org.springframework.stereotype.Service;

@Service
public interface OTPService {
    void saveOTP(OTP otp);
    void confirmOTP(String requestedToken);
    String generateOTP();
    OTP getOTP(String otp);
}
