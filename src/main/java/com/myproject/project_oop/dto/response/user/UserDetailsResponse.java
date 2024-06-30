package com.myproject.project_oop.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.dto.response.AbstractDataResponse;
import com.myproject.project_oop.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
public class UserDetailsResponse extends AbstractDataResponse {

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

    @JsonProperty("avatarFileName")
    private String avatarFileName;

    public static UserDetailsResponse buildFromUser(User user) {

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
                .avatarFileName(user.getAvatarFileName())
                .build();
    }

}
