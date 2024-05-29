package com.myproject.project_oop.response.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.model.File;
import com.myproject.project_oop.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class FriendResponse {

    @JsonProperty("friendId")
    private Integer friendId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    public static FriendResponse buildFromUser(User user) {

        File avatar = user.getAvatar();

        return FriendResponse.builder()
                .friendId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(avatar == null ? "" : avatar.getUrl())
                .build();
    }

}
