package com.practice.userService.serviceImpl;

import com.practice.userService.models.OTP;
import com.practice.userService.repos.OTPRepository;
import com.practice.userService.service.OTPService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class OTPServiceImpl implements OTPService {
    final OTPRepository otpRepository;

    @Transactional
    public void saveOTP(OTP otp){
        otpRepository.save(otp);
    }

    public String generateOTP() {
        Random random = new Random();
        int number = random.nextInt(10000);
        return String.format("%04d", number);
    }

    public void confirmOTP(String requestedToken) {
        Optional<OTP> optionalOTP = otpRepository.findByOtp(requestedToken);
        if (!optionalOTP.isPresent()) {
            throw new IllegalArgumentException("This Token Not Found");
        }
        optionalOTP.get().setConfirmedAt(LocalDateTime.now());
        otpRepository.save(optionalOTP.get());
    }

    public OTP getOTP(String requestedToken) {
        return otpRepository.findByOtp(requestedToken).get();
    }

}
