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
public class UserDetailsResponse {

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("about")
    private String about;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("address")
    private String address;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    public static UserDetailsResponse buildFromUser(User user) {

        File avatar = user.getAvatar();

        return UserDetailsResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .about(user.getAbout())
                .country(user.getCountry())
                .city(user.getCountry())
                .address(user.getAddress())
                .avatar(avatar == null ? "" : avatar.getFilename())
                .avatarUrl(avatar == null ? "" : avatar.getUrl())
                .build();
    }

}
