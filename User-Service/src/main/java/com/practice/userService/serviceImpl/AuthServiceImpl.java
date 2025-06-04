package com.practice.userService.serviceImpl;

import com.practice.userService.dtos.AuthRequest;
import com.practice.userService.models.AppUser;
import com.practice.userService.dtos.RegistrationRequest;
import com.practice.userService.models.OTP;
import com.practice.userService.security.JwtUtil;
import com.practice.userService.service.AuthService;
import com.practice.userService.service.EmailService;
import com.practice.userService.service.OTPService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserService userDetailsService;
    private final OTPService otpService;
    private final EmailService emailService;

    @Override
    public String login(AuthRequest authRequest) throws Exception {
        String userName = authRequest.getUserName();
        String password = authRequest.getPassword();
        System.out.println(userName);
        System.out.println(password);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userName,
                            password
                    ));
        } catch (BadCredentialsException e) {
            System.out.println("catch");
            throw new BadRequestException("Invalid Credentials", e);
        }
        System.out.println("before loadUserByUsername");
        AppUser appUser = userDetailsService.loadUserByUsername(userName);
        System.out.println("after loadUserByUsername");
        String token = jwtUtil.generateToken(appUser.getUsername(), appUser.getId().toString());
        System.out.println("token" + token);
        return token;
    }

    @Override
    public AppUser register(RegistrationRequest request) throws BadRequestException {
        Boolean isValidEmail = isValidEmail(request.getUserName());
        if(!isValidEmail){
            throw new IllegalArgumentException("UserName is not valid");
        }

        Boolean isValidPassword = isValidPassword(request.getPassword());
        if(!isValidPassword){
            throw new IllegalArgumentException("Password is not valid");
        }


        if(!request.getConfirmationPassword().equals(request.getPassword())){
            throw new BadRequestException("ConfirmationPassword should be equal to Password");
        }

        AppUser user = appUserService.createUser(new AppUser(
                request.getUserName(),
                request.getName(),
                request.getPassword(),
                request.getRole()
        ));

        String otp = otpService.generateOTP();
        otpService.saveOTP(new OTP(otp, user));
//        emailService.send(user.getUsername(), otpService.buildEmail(user.getName(), otp));
        emailService.sendEmail(user.getUsername(),"OTP EMAIL", "Welcome To TODA APP, This is your OTP : ",  otp);

        return user;
    }

    @Override
    public Boolean isValidEmail(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return userName.matches(emailRegex);
    }


    @Override
    public Boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }
}
