package com.practice.userService.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void send(String to, String htmlPage);
    void sendEmail(String toEmail, String subject, String text, String whatWillBeSent);
}
