package com.myproject.project_oop.controller;

import com.myproject.project_oop.constant.ErrorConstant;
import com.myproject.project_oop.constant.MessageConstant;
import com.myproject.project_oop.request.password.ResetPasswordRequest;
import com.myproject.project_oop.request.password.SendPasswordResetTokenRequest;
import com.myproject.project_oop.response.MessageResponse;
import com.myproject.project_oop.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/send-otp")
    public ResponseEntity<MessageResponse> sendPasswordResetEmail(
            @RequestBody SendPasswordResetTokenRequest request
    ) {
        var success = passwordResetService.sendResetPasswordToken(request.getEmail());
        var responseBody = MessageResponse.builder()
                .isError(success ? 0 : ErrorConstant.EMAIL_SEND_FAILED_CODE)
                .build();
        if (success) {
            responseBody.setMessage(MessageConstant.PASSWORD_OTP_SUCCESS);
        } else {
            responseBody.setErrorMessage(ErrorConstant.EMAIL_SEND_FAILED);
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/verify")
    public ResponseEntity<MessageResponse> verify(
            @RequestBody ResetPasswordRequest request
    ) {
        var success = passwordResetService.resetPassword(request);
        var responseBody = MessageResponse.builder()
                .isError(success ? 0 : ErrorConstant.UNEXPECTED_ERROR_CODE)
                .build();
        if (success) {
            responseBody.setMessage(MessageConstant.RESET_PASSWORD_SUCCESS);
        } else {
            responseBody.setErrorMessage(ErrorConstant.UNEXPECTED_ERROR);
        }
        return ResponseEntity.ok(responseBody);
    }

}
