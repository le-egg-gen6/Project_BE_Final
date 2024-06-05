package com.myproject.project_oop.dto.request.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MessageRequest {

    @JsonProperty("senderId")
    private Integer senderId;

    @JsonProperty("conversationId")
    private Integer conversationId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("content")
    private String content;

    @JsonProperty("imageUrl")
    private String imageUrl;

}
