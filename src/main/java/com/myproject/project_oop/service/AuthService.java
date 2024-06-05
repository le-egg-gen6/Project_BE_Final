package com.myproject.project_oop.service;

import com.myproject.project_oop.dto.request.auth.LoginRequest;
import com.myproject.project_oop.dto.request.auth.RegisterRequest;
import com.myproject.project_oop.dto.response.auth.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}
