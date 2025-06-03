package com.practice.userService.repos;


import com.practice.userService.models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByOtp(String otp);
}