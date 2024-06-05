package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.Participant;
import com.myproject.project_oop.repository.ParticipantRepository;
import com.myproject.project_oop.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    @Override
    public List<Participant> findByConversationId(Integer conversationId) {
        return participantRepository.findByConversationId(conversationId);
    }
}
