package com.myproject.project_oop.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.project_oop.config.security.jwt.JwtService;
import com.myproject.project_oop.constant.MessageConstant;
import com.myproject.project_oop.dto.response.BaseResponse;
import com.myproject.project_oop.service.AuthTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtService jwtService;

    private final AuthTokenService authTokenService;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String jwt = jwtService.extractTokenFromRequest(request);
        var success = authTokenService.revokeToken(jwt);
        if (success) {
            SecurityContextHolder.clearContext();
            response.setContentType("application/json");
            BaseResponse<?> msg = BaseResponse.builder()
                    .message(MessageConstant.LOGOUT_SUCCESS)
                    .success(true)
                    .build();
            String json = objectMapper.writeValueAsString(msg);
            response.getWriter().println(json);
        }
    }
}
