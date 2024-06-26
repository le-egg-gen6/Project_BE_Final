package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.config.error.exception.InvalidArgumentException;
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
import java.util.Objects;
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
                        var listMessage = messageRepository.findByConversationIdOrderById(conversation.getId());
                        var users = userService.findByConversationId(conversation.getId());
                        return ConversationDetailResponse.buildFromConversationAndMessage(conversation, listMessage, users, true);
                    }
                ).toList()
        );
        ret.addAll(
                listUnpinnedConversation.stream().map(
                        conversation -> {
                            var listMessage = messageRepository.findByConversationIdOrderById(conversation.getId());
                            var users = userService.findByConversationId(conversation.getId());
                            if (type == ConversationType.GROUP) {
                                return ConversationDetailResponse.buildFromConversationAndMessage(conversation, listMessage, users, false);
                            } else {
                                String name = "";
                                if (Objects.equals(users.get(0).getId(), user.getId())) {
                                    name = users.get(1).getFullName();
                                } else {
                                    name = users.get(0).getFullName();
                                }
                                return ConversationDetailResponse.buildFromConversationAndMessage(conversation, name, listMessage, users, false);
                            }
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
        var rawResponse = messageRepository.findByConversationIdOrderById(conversationId);
        List<MessageResponse> responses = new ArrayList<>();
        if (!rawResponse.isEmpty()) {
            responses.add(MessageResponse.buildFromMessage(rawResponse.get(0)));
        }
        if (rawResponse.size() > 1) {
            for (int i = 1; i < rawResponse.size(); ++i) {
                var pre = rawResponse.get(i - 1);
                var after = rawResponse.get(i);
                if (checkDateDiff(pre.getCreateAt(), after.getCreateAt())) {
                    responses.add(
                            MessageResponse.builder()
                                    .type("divider")
                                    .content(convertDateToString(after.getCreateAt()))
                                    .build()
                    );
                }
                responses.add(MessageResponse.buildFromMessage(after));
            }
        }
        return responses;
    }

    @Override
    public ConversationDetailResponse createConversation(CreateConversationRequest request) {
        var currentUser = userService.getUser();
        if (currentUser == null) {
            throw new AccessDeniedException("Access denied!");
        }
        var listMemberId = request.getMembers().stream().map(
                s -> {
                    if (request.getType().equals("DIRECT")) {
                        return Integer.valueOf(s);
                    } else {
                        return Integer.valueOf(s.substring(0, s.indexOf(':')));
                    }
                }
        ).toList();
        if (!listMemberId.contains(currentUser.getId())) {
            throw new InvalidArgumentException("Your group must contain you!");
        }
        Conversation conversation = Conversation.builder()
                .name(request.getName())
                .type(request.getType().equals("DIRECT") ? ConversationType.DIRECT : ConversationType.GROUP)
                .build();
        var saved_conversation = conversationRepository.save(conversation);
        var listUsers = listMemberId.stream()
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
        var listMessage = messageRepository.findByConversationIdOrderById(conversation.getId());
        if (final_conversation.getType() == ConversationType.GROUP) {
            return ConversationDetailResponse.buildFromConversationAndMessage(final_conversation, listMessage, listUsers, false);
        } else {
            String name = "";
            if (Objects.equals(listUsers.get(0).getId(), currentUser.getId())) {
                name = listUsers.get(1).getFullName();
            } else {
                name = listUsers.get(0).getFullName();
            }
            return ConversationDetailResponse.buildFromConversationAndMessage(final_conversation, name, listMessage, listUsers, false);
        }
    }
}
