package com.myproject.project_oop.request.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MessageRequest {

    @JsonProperty("senderId")
    private Integer senderId;

    @JsonProperty("roomId")
    private Integer roomId;

    @JsonProperty("content")
    private String content;

}
