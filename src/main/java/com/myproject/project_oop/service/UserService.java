package com.myproject.project_oop.service;

import com.myproject.project_oop.model.User;
import com.myproject.project_oop.request.user.UpdateUserRequest;
import com.myproject.project_oop.response.user.UserDetailsResponse;
import com.myproject.project_oop.response.user.UserResponse;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User findById(Integer id);

    User findByEmail(String email);

    User findByIdAndEmailVerificationToken(Integer id, String token);

    User findByIdAndEPasswordResetToken(Integer id, String token);

    User save(User user);

    User getUser();

    UserDetailsResponse updateUserDetails(UpdateUserRequest request);

    UserDetailsResponse getUserDetails();

    List<UserResponse> getAllVerifiedUser();

}
