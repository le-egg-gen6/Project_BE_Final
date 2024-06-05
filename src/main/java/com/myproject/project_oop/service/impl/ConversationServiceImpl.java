package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.Conversation;
import com.myproject.project_oop.repository.ConversationRepository;
import com.myproject.project_oop.dto.response.message.MessageResponse;
import com.myproject.project_oop.dto.response.conversation.ConversationDetailResponse;
import com.myproject.project_oop.service.ConversationService;
import com.myproject.project_oop.service.MessageService;
import com.myproject.project_oop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    private final UserService userService;

    private final MessageService messageService;

    @Override
    public Conversation getConversation(Integer id) {
        return conversationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Integer> getParticipantsId(Integer roomId) {
        return conversationRepository.getParticipantsByRoomId(roomId).stream()
                .map(participant -> participant.getUser().getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<ConversationDetailResponse> getAllConversationDetails() {
        var user = userService.getUser();
        var listConversation = conversationRepository.getAllConversation(user.getId());
        return listConversation.stream().map(
            conversation -> {
                var listMessage = messageService.getRawConversation(conversation.getId());
                var users = userService.findByConversationId(conversation.getId());
                return ConversationDetailResponse.buildFromConversationAndMessage(conversation, listMessage, users);
            }
        ).collect(Collectors.toList());
    }

    private boolean checkDateDiff(Date pre, Date after) {
        return !(pre.getDate() == after.getDate()
                && pre.getMonth() == after.getMonth()
                && pre.getYear() == after.getYear());
    }

    private String convertDateToString(Date date) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(date.getDate()))
                .append("/")
                .append(String.valueOf(date.getMonth()))
                .append("/")
                .append(String.valueOf(date.getYear()));
        return sb.toString();
    }

    @Override
    public List<MessageResponse> getConversationMessage(Integer conversationId) {
        var rawResponse = messageService.getConversation(conversationId);
        List<MessageResponse> responses = new ArrayList<>();
        if (rawResponse.size() == 1) {
            responses.add(rawResponse.get(0));
        }
        for (int i = 1; i < rawResponse.size(); ++i) {
            var pre = rawResponse.get(i - 1);
            var after = rawResponse.get(i);
            if (checkDateDiff(pre.getCreatedAt(), after.getCreatedAt())) {
                responses.add(
                        MessageResponse.builder()
                                .type("divider")
                                .content(convertDateToString(after.getCreatedAt()))
                                .build()
                );
            }
            responses.add(after);
        }
        return responses;
    }
}
