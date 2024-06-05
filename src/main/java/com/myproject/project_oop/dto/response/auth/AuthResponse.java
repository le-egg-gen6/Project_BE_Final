package com.myproject.project_oop.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.dto.response.AbstractDataResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
public class AuthResponse extends AbstractDataResponse {

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("isVerified")
    private Integer isVerified;

    @JsonProperty("accessToken")
    private String accessToken;

}

