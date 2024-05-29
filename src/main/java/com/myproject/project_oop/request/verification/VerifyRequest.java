package com.myproject.project_oop.request.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class VerifyRequest {

    @JsonProperty("userId")
    private Integer id;

    @JsonProperty("otp")
    private String otp;

}
