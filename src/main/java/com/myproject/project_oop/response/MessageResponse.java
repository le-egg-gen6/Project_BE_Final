package com.myproject.project_oop.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class MessageResponse extends BaseResponse {

    @JsonProperty("message")
    private String message;

}
