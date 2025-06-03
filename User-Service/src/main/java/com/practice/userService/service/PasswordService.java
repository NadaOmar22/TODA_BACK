package com.practice.userService.service;

import org.apache.coyote.BadRequestException;

public interface PasswordService {
    void forgetPassword(String userName);
    void resetPassword(String userName, String password, String confirmationPassword) throws BadRequestException;
}
