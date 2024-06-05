package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.repository.FriendRequestRepository;
import com.myproject.project_oop.dto.response.friend.FriendRequestResponse;
import com.myproject.project_oop.service.FriendRequestService;
import com.myproject.project_oop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final UserService userService;

    private final FriendRequestRepository friendRequestRepository;

    @Override
    public List<FriendRequestResponse> getAllFriendRequests() {
        var user = userService.getUser();
        if (user != null) {
            throw new AccessDeniedException("Denied Access");
        }
        var lists = friendRequestRepository.findAllByUserId(user.getId());
        return lists.stream().map(FriendRequestResponse::buildFromFriendRequest).collect(Collectors.toList());
    }
}
