package com.myproject.project_oop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friend_relationship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRelationship extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "friend_id")
    private Integer friend;

}
