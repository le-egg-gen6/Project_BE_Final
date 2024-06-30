package com.myproject.project_oop.controller;

import com.myproject.project_oop.constant.ErrorConstant;
import com.myproject.project_oop.constant.MessageConstant;
import com.myproject.project_oop.dto.request.verification.VerifyRequest;
import com.myproject.project_oop.dto.response.BaseResponse;
import com.myproject.project_oop.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("${api.prefix}" + "${api.version}" + "/email-verification")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/verify")
    public ResponseEntity<BaseResponse<?>> verifyUser(
            @RequestBody VerifyRequest request
    ) {
        var success = emailVerificationService.verify(request.getUserId(), request.getOtp());
        if (success) {
            return ResponseEntity.ok(
                    BaseResponse.buildMessageResponse(MessageConstant.VERIFY_SUCCESS)
            );
        } else {
            return ResponseEntity.ok(
                    BaseResponse.buildErrorResponse(ErrorConstant.OTP_WRONG)
            );
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<BaseResponse<?>> resendEmail() {
        var success = emailVerificationService.resendVerificationEmail();
        if (success) {
            return ResponseEntity.ok(
                    BaseResponse.buildMessageResponse(MessageConstant.EMAIL_RESEND_SUCCESS)
            );
        } else {
            return ResponseEntity.ok(
                    BaseResponse.buildErrorResponse(ErrorConstant.EMAIL_SEND_FAILED)
            );
        }
    }

}
