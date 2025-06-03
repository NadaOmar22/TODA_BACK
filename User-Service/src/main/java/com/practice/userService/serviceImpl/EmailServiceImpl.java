package com.practice.userService.serviceImpl;


import com.practice.userService.service.EmailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    final JavaMailSender mailSender;

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String FROM_EMAIL = "nadao7481@gmail.com";
    private static final String FROM_PASSWORD = "vanb dpku ozqn fcjv" ;// appName = JavaMailApp

    @Override
    @Async
    public void send(String to, String htmlPage) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom("nadao7481@gmail.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Confirm Your Email");
            mimeMessageHelper.setText(htmlPage, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException exception){
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void sendEmail(String toEmail, String subject, String text, String whatWillBeSent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", String.valueOf(SMTP_PORT));

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text + whatWillBeSent);

            Transport.send(message);

            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send Email");
        }
    }
}
