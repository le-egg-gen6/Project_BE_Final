package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.AuthToken;
import com.myproject.project_oop.model.User;
import com.myproject.project_oop.repository.AuthTokenRepository;
import com.myproject.project_oop.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    @Override
    public AuthToken findByToken(String token) {
        return authTokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        var token = AuthToken.builder()
                .user(user)
                .token(jwtToken)
                .expired(0)
                .revoked(0)
                .build();
        authTokenRepository.save(token);
    }

    @Override
    public void revokeAllUserToken(Integer id) {
        var validUserTokens = authTokenRepository.findAllValidTokenByUser(id);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(1);
            token.setRevoked(1);
        });
        authTokenRepository.saveAll(validUserTokens);
    }

    @Override
    public boolean revokeToken(String jwt) {
        var storedToken = authTokenRepository.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(1);
            storedToken.setRevoked(1);
            authTokenRepository.save(storedToken);
            return true;
        }
        return false;
    }
}
