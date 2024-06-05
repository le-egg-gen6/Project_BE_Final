package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.config.security.jwt.JwtService;
import com.myproject.project_oop.config.security.user.CustomUserDetails;
import com.myproject.project_oop.constant.ErrorConstant;
import com.myproject.project_oop.model.EmailVerificationToken;
import com.myproject.project_oop.model.User;
import com.myproject.project_oop.model.constant.Status;
import com.myproject.project_oop.dto.request.auth.LoginRequest;
import com.myproject.project_oop.dto.request.auth.RegisterRequest;
import com.myproject.project_oop.dto.response.auth.AuthResponse;
import com.myproject.project_oop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final AuthTokenService authTokenService;

    private final EmailVerificationTokenService emailVerificationTokenService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final EmailVerificationService emailVerificationService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        var user = userService.findByUsername(request.getUsername());
        if (user != null) {
            return AuthResponse.builder()
                    .success(false)
                    .message(ErrorConstant.USERNAME_DUPLICATE)
                    .build();
        }
        user = userService.findByEmail(request.getEmail());
        if (user != null) {
            return AuthResponse.builder()
                    .success(false)
                    .message(ErrorConstant.EMAIL_DUPLICATE)
                    .build();
        }
        var tmp_user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .status(Status.NOT_VERIFIED)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        var saved_user = userService.save(tmp_user);
        var emailVerificationToken_tmp = EmailVerificationToken.builder()
                .user(saved_user)
                .token(emailVerificationService.generateEmailVerificationToken(saved_user))
                .expired(0)
                .build();
        boolean success = emailVerificationService.sendVerificationEmail(
                saved_user.getId(),
                saved_user.getEmail(),
                saved_user.getUsername(),
                emailVerificationToken_tmp.getToken()
        );
        emailVerificationTokenService.save(emailVerificationToken_tmp);
        CustomUserDetails userDetails = CustomUserDetails.buildFromUser(saved_user);
        var jwtToken = jwtService.buildToken(userDetails);
        authTokenService.revokeAllUserToken(saved_user.getId());
        authTokenService.saveUserToken(saved_user, jwtToken);
        var response = AuthResponse.builder()
                .success(success)
                .build();
        if (success) {
            response.setUserId(saved_user.getId());
            response.setIsVerified(1);
            response.setAccessToken(jwtToken);
        } else {
            response.setMessage(ErrorConstant.EMAIL_SEND_FAILED);
        }
        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        var user = userService.findByUsername(request.getUsername());
        if (user != null) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            CustomUserDetails userDetails = CustomUserDetails.buildFromUser(user);
            var jwtToken = jwtService.buildToken(userDetails);
            authTokenService.revokeAllUserToken(user.getId());
            authTokenService.saveUserToken(user, jwtToken);
            return AuthResponse.builder()
                    .success(true)
                    .userId(user.getId())
                    .isVerified(user.getStatus() == Status.NOT_VERIFIED ? 1 : 0)
                    .accessToken(jwtToken)
                    .build();
        } else {
            return AuthResponse.builder()
                    .success(false)
                    .message(ErrorConstant.USERNAME_PASSWORD_WRONG)
                    .build();
        }
    }
}
