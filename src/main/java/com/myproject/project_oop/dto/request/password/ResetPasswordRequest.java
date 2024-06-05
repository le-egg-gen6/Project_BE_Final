package com.myproject.project_oop.dto.request.password;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ResetPasswordRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("confirmPassword")
    private String confirmPassword;

    @JsonProperty("otp")
    private String otp;

}
