package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.EmailVerificationToken;
import com.myproject.project_oop.model.PasswordResetToken;
import com.myproject.project_oop.repository.PasswordResetTokenRepository;
import com.myproject.project_oop.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken save(PasswordResetToken token) {
        return passwordResetTokenRepository.save(token);
    }

    @Override
    public void revokeAllPasswordResetToken(Integer id) {
        List<PasswordResetToken> passwordResetTokens = passwordResetTokenRepository.findAllValidTokenByUser(id);
        passwordResetTokens.forEach(
                token -> {
                    token.setExpired(1);
                }
        );
        passwordResetTokenRepository.saveAll(passwordResetTokens);
    }

    @Override
    public PasswordResetToken findByToken(String otp) {
        return passwordResetTokenRepository.findByToken(otp).orElse(null);
    }
}
