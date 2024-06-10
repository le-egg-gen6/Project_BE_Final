package com.myproject.project_oop.model;

import com.myproject.project_oop.model.constant.ConversationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "conversation")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conversation extends BaseModel {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ConversationType type;

    @OneToMany(mappedBy = "conversation")
    private Set<Participant> participants;

}
