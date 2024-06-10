package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.config.error.exception.InvalidArgumentException;
import com.myproject.project_oop.config.error.exception.ResourceNotFoundException;
import com.myproject.project_oop.dto.request.friendrequest.AcceptFriendRequest;
import com.myproject.project_oop.dto.request.friendrequest.SendFriendRequest;
import com.myproject.project_oop.model.FriendRelationship;
import com.myproject.project_oop.model.FriendRequest;
import com.myproject.project_oop.repository.FriendRelationshipRepository;
import com.myproject.project_oop.repository.FriendRequestRepository;
import com.myproject.project_oop.dto.response.friend.FriendRequestResponse;
import com.myproject.project_oop.service.FriendRequestService;
import com.myproject.project_oop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final UserService userService;

    private final FriendRequestRepository friendRequestRepository;

    private final FriendRelationshipRepository friendRelationshipRepository;

    @Override
    public List<FriendRequestResponse> getAllFriendRequests() {
        var user = userService.getUser();
        if (user == null) {
            throw new AccessDeniedException("Access Denied!");
        }
        var lists = friendRequestRepository.findAllFriendRequestByReceiverId(user.getId());
        return lists.stream().map(FriendRequestResponse::buildFromFriendRequest).collect(Collectors.toList());
    }

    @Override
    public void sendFriendRequest(SendFriendRequest request) {
        var currentUser = userService.getUser();
        if (
                currentUser == null
                || !Objects.equals(currentUser.getId(), request.getSenderId())
        ) {
            throw new AccessDeniedException("Access denied!");
        }
        var savedFriendRequest = friendRequestRepository.findByUserIdAndReceiverId(currentUser.getId(), request.getReceiverId());
        if (savedFriendRequest.isPresent()) {
            throw new InvalidArgumentException("You have sent friend request!");
        }
        var friendRequest = FriendRequest.builder()
                .user(currentUser)
                .receiverId(request.getReceiverId())
                .build();
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public void acceptFriendRequest(AcceptFriendRequest request) {
        var savedFriendRequest = friendRequestRepository.findById(request.getRequestId()).orElse(null);
        if (savedFriendRequest == null) {
            throw new ResourceNotFoundException("Request not existed!");
        }
        var currentUser = userService.getUser();
        if (
                currentUser == null
                || !Objects.equals(currentUser.getId(), savedFriendRequest.getReceiverId())
                || !Objects.equals(currentUser.getId(), request.getReceiverId())
        ) {
            throw new AccessDeniedException("Access denied!");
        }
        var sender = userService.findById(savedFriendRequest.getUser().getId());
        var friendRelationship_1 = FriendRelationship.builder()
                .user(currentUser)
                .friend(sender.getId())
                .build();
        var friendRelationship_2 = FriendRelationship.builder()
                .user(sender)
                .friend(currentUser.getId())
                .build();
        friendRelationshipRepository.save(friendRelationship_1);
        friendRelationshipRepository.save(friendRelationship_2);
        friendRequestRepository.delete(savedFriendRequest);
    }
}
