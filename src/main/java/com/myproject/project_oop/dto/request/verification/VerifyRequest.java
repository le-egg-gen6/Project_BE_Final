package com.myproject.project_oop.dto.request.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class VerifyRequest {

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("otp")
    private String otp;

}
