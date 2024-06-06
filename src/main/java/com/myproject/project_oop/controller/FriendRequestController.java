package com.myproject.project_oop.controller;

import com.myproject.project_oop.constant.MessageConstant;
import com.myproject.project_oop.dto.request.friendrequest.AcceptFriendRequest;
import com.myproject.project_oop.dto.request.friendrequest.SendFriendRequest;
import com.myproject.project_oop.dto.response.BaseResponse;
import com.myproject.project_oop.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/friend-request")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @GetMapping("/get-all-friend-request")
    public ResponseEntity<BaseResponse<?>> getALlFriendRequest() {
        return ResponseEntity.ok(
                BaseResponse.buildDataResponse(friendRequestService.getAllFriendRequests())
        );
    }

    @PostMapping("/send-friend-request")
    public ResponseEntity<BaseResponse<?>> sendFriendRequest(
            @RequestBody SendFriendRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.buildMessageResponse(MessageConstant.FRIEND_REQUEST_SENT_SUCCESS)
        );
    }

    @PostMapping("/accept-friend-request")
    public ResponseEntity<BaseResponse<?>> acceptFriendRequest(
            @RequestBody AcceptFriendRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.buildMessageResponse(MessageConstant.FRIEND_REQUEST_ACCEPTED)
        );
    }

}
