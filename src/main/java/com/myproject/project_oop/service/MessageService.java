package com.myproject.project_oop.service;

import com.myproject.project_oop.model.Message;
import com.myproject.project_oop.dto.request.message.MessageRequest;
import com.myproject.project_oop.dto.response.message.MessageResponse;

import java.util.List;

public interface MessageService {

    Message save(Message message);

    Message saveNewMessage(MessageRequest request);

    List<MessageResponse> getConversation(Integer conversationId);

    List<Message> getRawConversation(Integer conversationId);

}
