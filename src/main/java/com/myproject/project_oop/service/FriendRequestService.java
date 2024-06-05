package com.myproject.project_oop.service;

import com.myproject.project_oop.dto.response.friend.FriendRequestResponse;

import java.util.List;

public interface FriendRequestService {

    List<FriendRequestResponse> getAllFriendRequests();

}
