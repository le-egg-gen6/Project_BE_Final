package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.Message;
import com.myproject.project_oop.model.constant.MessageType;
import com.myproject.project_oop.repository.ConversationRepository;
import com.myproject.project_oop.repository.MessageRepository;
import com.myproject.project_oop.dto.request.message.MessageRequest;
import com.myproject.project_oop.dto.response.message.MessageResponse;
import com.myproject.project_oop.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final ConversationRepository conversationRepository;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message saveNewMessage(MessageRequest request) {
        var conversation = conversationRepository.findById(request.getConversationId()).orElse(null);
        if (conversation != null) {
            var newMessage = Message.builder()
                    .conversation(conversation)
                    .content(request.getContent())
                    .type(request.getType().equals("TEXT") ? MessageType.TEXT : MessageType.IMAGE)
                    .senderId(request.getSenderId())
                    .createAt(new Date(System.currentTimeMillis()))
                    .build();
            return messageRepository.save(newMessage);
        } else {
            return null;
        }
    }

    @Override
    public List<MessageResponse> getConversation(Integer conversationId) {
        var listMessage = messageRepository.findByConversationIdOrderById(conversationId);
        return listMessage.stream()
                .map(MessageResponse::buildFromMessage)
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getRawConversation(Integer conversationId) {
        return messageRepository.findByConversationIdOrderById(conversationId);
    }
}
