package com.myproject.project_oop.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

}
