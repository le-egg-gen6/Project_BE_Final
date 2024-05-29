package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.config.error.exception.ResourceNotFoundException;
import com.myproject.project_oop.model.Message;
import com.myproject.project_oop.repository.MessageRepository;
import com.myproject.project_oop.request.message.MessageRequest;
import com.myproject.project_oop.service.MessageService;
import com.myproject.project_oop.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final RoomService roomService;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message saveNewMessage(MessageRequest request) {
        var room = roomService.getRoom(request.getRoomId());
        if (room != null) {
            var newMessage = Message.builder()
                    .room(room)
                    .content(request.getContent())
                    .senderId(request.getSenderId())
                    .build();
            return messageRepository.save(newMessage);
        } else {
            return null;
        }
    }
}
