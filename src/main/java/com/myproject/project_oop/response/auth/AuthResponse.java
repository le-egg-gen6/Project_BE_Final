package com.myproject.project_oop.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.response.BaseResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
public class AuthResponse extends BaseResponse {

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("isVerified")
    private Integer isVerified;

    @JsonProperty("token")
    private String token;

}

