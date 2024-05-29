package com.myproject.project_oop.service;

import com.myproject.project_oop.model.PasswordResetToken;

public interface PasswordResetTokenService {

    void revokeAllPasswordResetToken(Integer id);

    PasswordResetToken findByToken(String otp);

    PasswordResetToken save(PasswordResetToken token);

}
