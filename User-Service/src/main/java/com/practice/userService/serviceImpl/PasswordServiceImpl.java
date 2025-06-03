package com.practice.userService.serviceImpl;


import com.practice.userService.models.AppUser;
import com.practice.userService.service.AuthService;
import com.practice.userService.service.EmailService;
import com.practice.userService.service.PasswordService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@AllArgsConstructor
@Service
public class PasswordServiceImpl implements PasswordService {
    AppUserService appUserService;
    EmailService emailService;
    BCryptPasswordEncoder passwordEncoder;
    AuthService authService;

    @Override
    public void forgetPassword(String userName){
        AppUser user = appUserService.loadUserByUsername(userName);
        if(user == null){
            throw new UsernameNotFoundException(userName);
        }
        String newPassword = generatePassword(8);
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        appUserService.saveUser(user);
        emailService.sendEmail(userName, "Dummy Password EMAIL" ,"Welcome to TODA APP please use this password to login and reset it ASAP",newPassword);
    }

    @Override
    public void resetPassword(String userName, String password, String confirmationPassword) throws BadRequestException {
        AppUser user = appUserService.loadUserByUsername(userName);
        if(user == null){
            throw new UsernameNotFoundException(userName);
        }

        authService.isValidPassword(password);

        if(!confirmationPassword.equals(password)){
            throw new BadRequestException("ConfirmationPassword should be equal to Password");
        }
        user.setPassword(passwordEncoder.encode(password));
        appUserService.saveUser(user);
    }

    public static String generatePassword(int length) {
        String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String LOWER = "abcdefghijklmnopqrstuvwxyz";
        String DIGITS = "0123456789";
        String SPECIAL = "@$!%*?&";
        String ALL = UPPER + LOWER + DIGITS + SPECIAL;

        SecureRandom random = new SecureRandom();

        if (length < 8) {
            throw new IllegalArgumentException("Password length should be at least 8 characters.");
        }

        StringBuilder password = new StringBuilder();

        // Ensure each required character type is included
        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        for (int i = 4; i < length; i++) {
            password.append(ALL.charAt(random.nextInt(ALL.length())));
        }

        char[] characters = password.toString().toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }

        return new String(characters);
    }
}
