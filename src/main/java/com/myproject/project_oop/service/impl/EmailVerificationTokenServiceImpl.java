package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.EmailVerificationToken;
import com.myproject.project_oop.repository.EmailVerificationTokenRepository;
import com.myproject.project_oop.service.EmailVerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Override
    public void revokeAllEmailVerificationToken(Integer id) {
        List<EmailVerificationToken> emailVerificationTokenList = emailVerificationTokenRepository.findAllValidTokenByUser(id);
        emailVerificationTokenList.forEach(
                token -> {
                    token.setExpired(1);
                }
        );
        emailVerificationTokenRepository.saveAll(emailVerificationTokenList);
    }

    @Override
    public EmailVerificationToken save(EmailVerificationToken token) {
        return emailVerificationTokenRepository.save(token);
    }

    @Override
    public EmailVerificationToken findByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token).orElse(null);
    }
}
