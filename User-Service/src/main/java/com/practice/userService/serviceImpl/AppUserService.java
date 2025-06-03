package com.practice.userService.serviceImpl;


import com.practice.userService.models.AppUser;
import com.practice.userService.models.OTP;
import com.practice.userService.repos.AppUserRepository;
import com.practice.userService.service.EmailService;
import com.practice.userService.service.OTPService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    final AppUserRepository userRepository;
    final BCryptPasswordEncoder passwordEncoder;
    final OTPService otpService;
    final EmailService emailService;

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException(username));
    }

    public AppUser getUserById(Long id) throws UsernameNotFoundException, ChangeSetPersister.NotFoundException {
        return userRepository.findById(id).orElseThrow(()-> new ChangeSetPersister.NotFoundException());
    }

    public List<AppUser> getAllUsers() throws UsernameNotFoundException, ChangeSetPersister.NotFoundException {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long id) throws UsernameNotFoundException, ChangeSetPersister.NotFoundException {
        AppUser appUser = userRepository.findById(id).orElseThrow(()-> new ChangeSetPersister.NotFoundException());
        userRepository.delete(appUser);
    }

    @Transactional
    public AppUser createUser(AppUser appUser){
        boolean isExists = userRepository.findByUserName(appUser.getUsername()).isPresent();
        if(isExists){
            throw new IllegalStateException("UserName already exists");
        }

        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        return appUser;
    }

    @Transactional
    public AppUser updateUser(AppUser appUser) throws BadRequestException, ChangeSetPersister.NotFoundException {
        if(appUser.getId() == null){
            throw new BadRequestException("User id is required in update");
        }

        AppUser savedAppUser = getUserById(appUser.getId());

        if (appUser.getRole() != null && !appUser.getRole().equals(savedAppUser.getRole())) {
            savedAppUser.setRole(appUser.getRole());
        }

        if (appUser.getPassword() != null && !appUser.getPassword().equals(savedAppUser.getPassword())) {
            savedAppUser.setPassword(appUser.getPassword());
        }

        if (appUser.getUsername() != null && !appUser.getUsername().equals(savedAppUser.getUsername())) {
            savedAppUser.setUserName(appUser.getUsername());
        }

        return userRepository.save(savedAppUser);
    }

    @Transactional
    public void enableUser(String otp){
        OTP otpObj = otpService.getOTP(otp);
        Optional<AppUser> optionalAppUser = userRepository.findById(otpObj.getUser().getId());
        AppUser user = optionalAppUser.get();
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional
    public void saveUser(AppUser user){
        userRepository.save(user);
    }

}