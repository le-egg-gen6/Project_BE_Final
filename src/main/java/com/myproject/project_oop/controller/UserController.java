package com.myproject.project_oop.controller;

import com.myproject.project_oop.dto.request.user.UpdateUserRequest;
import com.myproject.project_oop.dto.response.user.UserDetailsResponse;
import com.myproject.project_oop.dto.response.user.UserResponse;
import com.myproject.project_oop.service.FriendRequestService;
import com.myproject.project_oop.service.FriendService;
import com.myproject.project_oop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final FriendService friendService;

    private final FriendRequestService friendRequestService;

    @GetMapping("/get-me")
    public ResponseEntity<UserDetailsResponse> getUserDetails() {
        return ResponseEntity.ok(userService.getUserDetails());
    };

    @PostMapping("/update-me")
    public ResponseEntity<UserDetailsResponse> updateRegisteredUser(
            @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.ok(userService.updateUserDetails(request));
    }

    @GetMapping("/get-all-verified-user")
    public ResponseEntity<List<UserResponse>> getAllVerifiedUser() {
        return ResponseEntity.ok(userService.getAllVerifiedUser());
    }

}
