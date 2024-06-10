package com.myproject.project_oop.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "participant")
@AllArgsConstructor
@NoArgsConstructor
public class Participant extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @Column(name = "pinned")
    private boolean pinned;

}
