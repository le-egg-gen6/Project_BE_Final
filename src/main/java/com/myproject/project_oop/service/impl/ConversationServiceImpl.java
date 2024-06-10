package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.dto.request.conversation.CreateConversationRequest;
import com.myproject.project_oop.model.Conversation;
import com.myproject.project_oop.model.Participant;
import com.myproject.project_oop.model.constant.ConversationType;
import com.myproject.project_oop.repository.ConversationRepository;
import com.myproject.project_oop.dto.response.message.MessageResponse;
import com.myproject.project_oop.dto.response.conversation.ConversationDetailResponse;
import com.myproject.project_oop.repository.MessageRepository;
import com.myproject.project_oop.repository.ParticipantRepository;
import com.myproject.project_oop.service.ConversationService;
import com.myproject.project_oop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    private final ParticipantRepository participantRepository;

    private final MessageRepository messageRepository;

    private final UserService userService;

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
    public List<ConversationDetailResponse> getAllDirectConversationDetails() {
        return getAllConversationDetails(ConversationType.DIRECT);
    }

    @Override
    public List<ConversationDetailResponse> getAllGroupConversationDetails() {
        return getAllConversationDetails(ConversationType.GROUP);
    }


    private List<ConversationDetailResponse> getAllConversationDetails(ConversationType type) {
        var user = userService.getUser();
        if (user == null) {
            throw new AccessDeniedException("Access Denied!");
        }
        var listPinnedConversation = conversationRepository.getAllConversation(user.getId(), type, true);
        var listUnpinnedConversation = conversationRepository.getAllConversation(user.getId(), type, false);
        var ret = new ArrayList<>(
                listPinnedConversation.stream().map(
                    conversation -> {
                        var listMessage = messageRepository.findByConversationIdOrderByCreateAtAsc(conversation.getId());
                        var users = userService.findByConversationId(conversation.getId());
                        return ConversationDetailResponse.buildFromConversationAndMessage(conversation, listMessage, users, true);
                    }
                ).toList()
        );
        ret.addAll(
                listUnpinnedConversation.stream().map(
                        conversation -> {
                            var listMessage = messageRepository.findByConversationIdOrderByCreateAtAsc(conversation.getId());
                            var users = userService.findByConversationId(conversation.getId());
                            return ConversationDetailResponse.buildFromConversationAndMessage(conversation, listMessage, users, false);
                        }
                ).toList()
        );
        return ret;
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
        var rawResponse = messageRepository.findByConversationIdOrderByCreateAtAsc(conversationId).stream()
                .map(MessageResponse::buildFromMessage)
                .toList();
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

    @Override
    public ConversationDetailResponse createConversation(CreateConversationRequest request) {
        var currentUser = userService.getUser();
        if (currentUser == null) {
            throw new AccessDeniedException("Access denied!");
        }
        if (!request.getGroupMemberId().contains(currentUser.getId())) {
            return ConversationDetailResponse.builder()
                    .success(false)
                    .message("You cannot create group without you!")
                    .build();
        }
        Conversation conversation = Conversation.builder()
                .name(request.getName())
                .type(request.getType().equals("DIRECT") ? ConversationType.DIRECT : ConversationType.GROUP)
                .build();
        var saved_conversation = conversationRepository.save(conversation);
        var listUsers = request.getGroupMemberId().stream()
                .map(
                        userService::findById
                ).collect(Collectors.toList());
        saved_conversation.setParticipants(
               listUsers.stream().map(
                       user -> {
                           var participant = Participant.builder()
                                   .conversation(saved_conversation)
                                   .user(user)
                                   .pinned(false)
                                   .build();
                           return participantRepository.save(participant);
                       }
               ).collect(Collectors.toSet())
        );
        var final_conversation = conversationRepository.save(saved_conversation);
        var listMessage = messageRepository.findByConversationIdOrderByCreateAtAsc(conversation.getId());
        return ConversationDetailResponse.buildFromConversationAndMessage(final_conversation, listMessage, listUsers, false);
    }
}
