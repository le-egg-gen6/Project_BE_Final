package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.repository.FriendRelationshipRepository;
import com.myproject.project_oop.response.friend.FriendResponse;
import com.myproject.project_oop.service.FriendService;
import com.myproject.project_oop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserService userService;

    private final FriendRelationshipRepository friendRelationshipRepository;

    @Override
    public List<FriendResponse> getAllFriends() {
        var current_user = userService.getUser();
        if (current_user == null) {
            throw new AccessDeniedException("Access Denied");
        }
        List<Integer> friendIds = friendRelationshipRepository.getAllFriendId(current_user.getId());
        return friendIds.stream().map(
                userService::findById
        ).filter(Objects::nonNull).map(FriendResponse::buildFromUser).collect(Collectors.toList());

    }
}
