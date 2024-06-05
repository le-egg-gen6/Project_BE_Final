package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    List<Participant> findByConversationId(Integer conversationId);

}
