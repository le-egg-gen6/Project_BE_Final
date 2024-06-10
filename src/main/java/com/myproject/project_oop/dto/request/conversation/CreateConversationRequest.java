package com.myproject.project_oop.dto.request.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class CreateConversationRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("groupMemberId")
    private List<Integer> groupMemberId;

}
