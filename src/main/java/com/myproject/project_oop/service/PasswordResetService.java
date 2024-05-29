package com.myproject.project_oop.service;

import com.myproject.project_oop.model.User;
import com.myproject.project_oop.request.password.ResetPasswordRequest;

public interface PasswordResetService {

    String generatePasswordResetToken(User user);

    boolean resetPassword(ResetPasswordRequest request);

    boolean sendResetPasswordToken(String email);

}
