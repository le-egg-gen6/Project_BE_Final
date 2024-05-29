package com.myproject.project_oop.service;

import com.myproject.project_oop.model.User;

public interface EmailVerificationService {

    String generateEmailVerificationToken(User user);

    boolean verify(Integer id, String emailVerificationToken);

    boolean sendVerificationEmail(Integer id, String address, String fullName, String otp);

    boolean resendVerificationEmail();

}
