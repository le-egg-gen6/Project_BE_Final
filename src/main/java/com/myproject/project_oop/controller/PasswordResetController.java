package com.myproject.project_oop.controller;

import com.myproject.project_oop.constant.ErrorConstant;
import com.myproject.project_oop.constant.MessageConstant;
import com.myproject.project_oop.dto.request.password.ResetPasswordRequest;
import com.myproject.project_oop.dto.request.password.SendPasswordResetTokenRequest;
import com.myproject.project_oop.dto.response.BaseResponse;
import com.myproject.project_oop.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/send-otp")
    public ResponseEntity<BaseResponse<?>> sendPasswordResetEmail(
            @RequestBody SendPasswordResetTokenRequest request
    ) {
        var success = passwordResetService.sendResetPasswordToken(request.getEmail());
        if (success) {
            return ResponseEntity.ok(
                    BaseResponse.buildMessageResponse(MessageConstant.PASSWORD_OTP_SUCCESS)
            );
        } else {
            return ResponseEntity.ok(
                    BaseResponse.buildErrorResponse(ErrorConstant.EMAIL_SEND_FAILED)
            );
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<BaseResponse<?>> verify(
            @RequestBody ResetPasswordRequest request
    ) {
        var success = passwordResetService.resetPassword(request);
        if (success) {
            return ResponseEntity.ok(
                    BaseResponse.buildMessageResponse(MessageConstant.RESET_PASSWORD_SUCCESS)
            );
        } else {
            return ResponseEntity.ok(
                    BaseResponse.buildErrorResponse(ErrorConstant.RESET_PASSWORD_FAILED)
            );
        }
    }

}
