package com.myproject.project_oop.service;

import com.myproject.project_oop.request.auth.LoginRequest;
import com.myproject.project_oop.request.auth.RegisterRequest;
import com.myproject.project_oop.response.MessageResponse;
import com.myproject.project_oop.response.auth.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}
