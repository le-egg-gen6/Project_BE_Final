package com.myproject.project_oop.service;

import com.myproject.project_oop.dto.request.friendrequest.AcceptFriendRequest;
import com.myproject.project_oop.dto.request.friendrequest.SendFriendRequest;
import com.myproject.project_oop.dto.response.friend.FriendRequestResponse;

import java.util.List;

public interface FriendRequestService {

    List<FriendRequestResponse> getAllFriendRequests();

    void sendFriendRequest(SendFriendRequest request);

    void acceptFriendRequest(AcceptFriendRequest request);

}
