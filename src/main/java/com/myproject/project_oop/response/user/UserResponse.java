package com.myproject.project_oop.response.user;

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
public class UserResponse {

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    public static UserResponse buildFromUser(User user) {

        File avatar = user.getAvatar();

        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .avatarUrl(avatar == null ? "" : avatar.getUrl())
                .build();
    }

}
