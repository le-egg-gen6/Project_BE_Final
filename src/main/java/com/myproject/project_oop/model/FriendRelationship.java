package com.myproject.project_oop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "friend_relationship")
public class FriendRelationship extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "friend_id")
    private Integer friend;

}
