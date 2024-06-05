package com.myproject.project_oop.dto.response.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.model.FriendRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
public class FriendRequestResponse {

    @JsonProperty("requestId")
    private Integer requestId;

    @JsonProperty("senderId")
    private Integer senderId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("fullName")
    private String fullName;

    public static FriendRequestResponse buildFromFriendRequest(FriendRequest friendRequest) {
        return FriendRequestResponse.builder()
                .requestId(friendRequest.getId())
                .senderId(friendRequest.getUser().getId())
                .username(friendRequest.getUser().getUsername())
                .fullName(friendRequest.getUser().getFullName())
                .build();
    }

}
