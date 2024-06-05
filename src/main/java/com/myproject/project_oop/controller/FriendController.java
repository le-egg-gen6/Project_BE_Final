package com.myproject.project_oop.controller;

import com.myproject.project_oop.dto.response.BaseResponse;
import com.myproject.project_oop.dto.response.friend.FriendResponse;
import com.myproject.project_oop.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/get-all-friends")
    public ResponseEntity<BaseResponse<?>> getAllFriends() {
        return ResponseEntity.ok(
                BaseResponse.buildDataResponse(friendService.getAllFriends())
        );
    }

}
