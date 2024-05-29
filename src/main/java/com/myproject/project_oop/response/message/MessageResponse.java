package com.myproject.project_oop.response.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.model.Message;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class MessageResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("senderId")
    private Integer senderId;

    @JsonProperty("roomId")
    private Integer roomId;

    @JsonProperty("content")
    private String content;

    public static MessageResponse buildFromMessage(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .roomId(message.getRoom().getId())
                .senderId(message.getSenderId())
                .build();
    }

}
