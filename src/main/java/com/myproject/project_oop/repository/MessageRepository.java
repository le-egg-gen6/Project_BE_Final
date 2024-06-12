package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByConversationIdOrderById(Integer conversationId);

}
