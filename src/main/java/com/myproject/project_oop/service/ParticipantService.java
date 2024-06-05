package com.myproject.project_oop.service;

import com.myproject.project_oop.model.Participant;

import java.util.List;

public interface ParticipantService {

    List<Participant> findByConversationId(Integer conversationId);

}
