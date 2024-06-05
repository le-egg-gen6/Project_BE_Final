package com.myproject.project_oop.controller;

import com.myproject.project_oop.dto.response.friend.FriendRequestResponse;
import com.myproject.project_oop.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/friend-request")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @GetMapping("/get-all-friend-request")
    public ResponseEntity<List<FriendRequestResponse>> getALlFriendRequest() {
        return ResponseEntity.ok(friendRequestService.getAllFriendRequests());
    }



}
