package com.myproject.project_oop.controller;

import com.myproject.project_oop.dto.request.auth.LoginRequest;
import com.myproject.project_oop.dto.request.auth.RegisterRequest;
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
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

//
//    @PostMapping("/forgot-password")
//
//
//    @PostMapping("/reset-password")
//

}
