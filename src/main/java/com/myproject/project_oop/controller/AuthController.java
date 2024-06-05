package com.myproject.project_oop.controller;

import com.myproject.project_oop.dto.request.auth.LoginRequest;
import com.myproject.project_oop.dto.request.auth.RegisterRequest;
import com.myproject.project_oop.dto.response.BaseResponse;
import com.myproject.project_oop.dto.response.auth.AuthResponse;
import com.myproject.project_oop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<?>> register(
            @RequestBody RegisterRequest request
    ) {
        var rawData = authService.register(request);
        if (rawData.isSuccess()) {
            return ResponseEntity.ok(
                    BaseResponse.buildDataResponse(rawData, rawData.getMessage())
            );
        } else {
            return ResponseEntity.ok(
                    BaseResponse.buildErrorResponse(rawData.getMessage())
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<?>> login(
            @RequestBody LoginRequest request
    ) {
        var rawData = authService.login(request);
        if (rawData.isSuccess()) {
            return ResponseEntity.ok(
                    BaseResponse.buildDataResponse(rawData, rawData.getMessage())
            );
        } else {
            return ResponseEntity.ok(
                    BaseResponse.buildErrorResponse(rawData.getMessage())
            );
        }
    }

//
//    @PostMapping("/forgot-password")
//
//
//    @PostMapping("/reset-password")
//

}
