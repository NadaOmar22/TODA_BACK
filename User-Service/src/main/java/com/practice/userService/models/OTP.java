package com.practice.userService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OTP {
    @SequenceGenerator(name = "otp_sequence", sequenceName =  "otp_sequence" , allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "otp_sequence")
    Long id;

    @Column(nullable = false)
    String otp;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime expiredAt;

    LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    AppUser user;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.expiredAt = this.createdAt.plusMinutes(15);
    }

    public OTP(String otp, AppUser user) {
        this.otp = otp;
        this.user = user;
    }
}
