package com.myproject.project_oop.dto.request.friendrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AcceptFriendRequest {

    @JsonProperty("requestId")
    private Integer requestId;

    @JsonProperty("senderId")
    private Integer senderId;

    @JsonProperty("receiverId")
    private Integer receiverId;

}
