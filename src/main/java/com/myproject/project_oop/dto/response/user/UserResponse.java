package com.myproject.project_oop.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.model.File;
import com.myproject.project_oop.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
public class UserResponse {

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("isSentFriendRequest")
    private boolean isSentFriendRequest;

    public static UserResponse buildFromUser(User user) {

        File avatar = user.getAvatar();

        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .avatarUrl(avatar == null ? null : avatar.getUrl())
                .build();
    }

}
