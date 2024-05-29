package com.myproject.project_oop.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class Message extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Room room;

    @Column(name = "sender_id")
    private Integer senderId;

//    @Column(name = "type")
//    @Enumerated(EnumType.STRING)
//    private MessageType type;

    @Column(name = "content")
    private String content;

}
