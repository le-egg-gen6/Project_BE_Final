package com.myproject.project_oop.dto.response.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.project_oop.dto.response.AbstractDataResponse;
import com.myproject.project_oop.model.Conversation;
import com.myproject.project_oop.model.Message;
import com.myproject.project_oop.model.User;
import com.myproject.project_oop.dto.response.message.MessageResponse;
import com.myproject.project_oop.dto.response.user.UserResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Getter
@Setter
@SuperBuilder
public class ConversationDetailResponse extends AbstractDataResponse {

    @JsonProperty("conversationId")
    private Integer conversationId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("lastArrivedMessage")
    private MessageResponse lastArrivedMessage;

    @JsonProperty("participants")
    private List<UserResponse> participants;

    @JsonProperty("pinned")
    private boolean pinned;

    public static ConversationDetailResponse buildFromConversationAndMessage(
            Conversation conversation,
            List<Message> messageList,
            List<User> users,
            boolean pinned
    ) {
        return ConversationDetailResponse.builder()
                .conversationId(conversation.getId())
//                .avatarUrl(conversation.)
                .name(
                        conversation.getName() != null ? conversation.getName() : null
                )
                .lastArrivedMessage(
                        messageList.isEmpty() ? null : MessageResponse.buildFromMessage(messageList.get(messageList.size() - 1))
                )
                .participants(
                        users.stream().map(
                                UserResponse::buildFromUser
                        ).toList()
                )
                .pinned(pinned)
                .build();
    }

}
