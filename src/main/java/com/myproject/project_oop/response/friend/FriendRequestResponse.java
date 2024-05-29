package com.myproject.project_oop.response.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.model.FriendRequest;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class FriendRequestResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("fullName")
    private String fullName;

    public static FriendRequestResponse buildFromFriendRequest(FriendRequest friendRequest) {
        return FriendRequestResponse.builder()
                .id(friendRequest.getId())
                .fullName(friendRequest.getUser().getFullName())
                .build();
    }

}
