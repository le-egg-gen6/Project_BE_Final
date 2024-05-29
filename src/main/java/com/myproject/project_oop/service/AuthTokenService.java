package com.myproject.project_oop.service;

import com.myproject.project_oop.model.AuthToken;
import com.myproject.project_oop.model.User;

public interface AuthTokenService {

    AuthToken findByToken(String token);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserToken(Integer id);

    boolean revokeToken(String jwt);

}
