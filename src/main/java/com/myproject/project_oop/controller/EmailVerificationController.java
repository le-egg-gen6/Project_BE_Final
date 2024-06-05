package com.myproject.project_oop.controller;

import com.myproject.project_oop.constant.ErrorConstant;
import com.myproject.project_oop.constant.MessageConstant;
import com.myproject.project_oop.dto.request.verification.VerifyRequest;
import com.myproject.project_oop.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin("*")
@Controller
@RequestMapping("${api.prefix}" + "${api.version}" + "/email-verification")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/verify")
    public ResponseEntity<MessageResponse> verifyUser(
            @RequestBody VerifyRequest request
    ) {
        var success = emailVerificationService.verify(request.getId(), request.getOtp());
        var responseBody = MessageResponse.builder()
                .isError(success ? 0 : ErrorConstant.OTP_WRONG_CODE)
                .build();
        if (success) {
            responseBody.setMessage(MessageConstant.VERIFY_SUCCESS);
        } else {
            responseBody.setErrorMessage(ErrorConstant.OTP_WRONG);
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/resend")
    public ResponseEntity<MessageResponse> resendEmail() {
        var success = emailVerificationService.resendVerificationEmail();
        var responseBody = MessageResponse.builder()
                .isError(success ? 0 : ErrorConstant.EMAIL_SEND_FAILED_CODE)
                .build();
        if (success) {
            responseBody.setMessage(MessageConstant.EMAIL_RESEND_SUCCESS);
        } else {
            responseBody.setErrorMessage(ErrorConstant.EMAIL_SEND_FAILED);
        }
        return ResponseEntity.ok(responseBody);
    }

}
