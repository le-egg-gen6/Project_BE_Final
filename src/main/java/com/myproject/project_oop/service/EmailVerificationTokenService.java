package com.myproject.project_oop.service;

import com.myproject.project_oop.model.EmailVerificationToken;

public interface EmailVerificationTokenService {

    void revokeAllEmailVerificationToken(Integer id);

    EmailVerificationToken save(EmailVerificationToken token);

    EmailVerificationToken findByToken(String token);

}
