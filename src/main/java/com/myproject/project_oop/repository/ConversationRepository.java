package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.Conversation;
import com.myproject.project_oop.model.Participant;
import com.myproject.project_oop.model.constant.ConversationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    Optional<Conversation> findById(Integer id);

    @Query(
        value = "select p from Participant p where p.conversation.id = :id"
    )
    List<Participant> getParticipantsByRoomId(Integer id);

    @Query(
            value = "select c from Participant p join Conversation c on p.conversation.id = c.id where c.type = :type and p.user.id = :id and p.pinned = :pinned "
    )
    List<Conversation> getAllConversation(Integer id, ConversationType type, boolean pinned);



}
