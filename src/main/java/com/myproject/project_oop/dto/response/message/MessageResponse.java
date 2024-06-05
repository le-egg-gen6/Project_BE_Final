package com.myproject.project_oop.dto.response.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.model.Message;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
public class MessageResponse {

    @JsonProperty("messageId")
    private Integer messageId;

    @JsonProperty("senderId")
    private Integer senderId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("subtype")
    private String subtype;

    @JsonProperty("conversationId")
    private Integer conversationId;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("content")
    private String content;

    @JsonProperty("imageUrl")
    private String imageUrl;

    public static MessageResponse buildFromMessage(Message message) {
        return MessageResponse.builder()
                .messageId(message.getId())
                .content(message.getContent())
                .type("msg")
                .subtype(message.getType().name())
                .conversationId(message.getConversation().getId())
                .createdAt(message.getCreateAt())
                .senderId(message.getSenderId())
                .imageUrl(message.getImageUrl())
                .build();
    }

}
