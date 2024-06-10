package com.myproject.project_oop.service;

import com.myproject.project_oop.dto.request.conversation.CreateConversationRequest;
import com.myproject.project_oop.model.Conversation;
import com.myproject.project_oop.dto.response.conversation.ConversationDetailResponse;
import com.myproject.project_oop.dto.response.message.MessageResponse;

import java.util.List;

public interface ConversationService {

    Conversation getConversation(Integer id);

    List<Integer> getParticipantsId(Integer roomId);

    List<ConversationDetailResponse> getAllDirectConversationDetails();

    List<ConversationDetailResponse> getAllGroupConversationDetails();

    List<MessageResponse> getConversationMessage(Integer conversationId);

    ConversationDetailResponse createConversation(CreateConversationRequest request);

}
