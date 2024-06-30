package com.myproject.project_oop.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateUserRequest {

    @JsonProperty("userId")
    private Integer id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

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

}
