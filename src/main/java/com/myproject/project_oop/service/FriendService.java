package com.myproject.project_oop.service;

import com.myproject.project_oop.response.friend.FriendResponse;

import java.util.List;

public interface FriendService {

    List<FriendResponse> getAllFriends();

}
