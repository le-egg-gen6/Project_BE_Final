package com.myproject.project_oop.dto.request.password;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SendPasswordResetTokenRequest {

    @JsonProperty("email")
    private String email;

}
