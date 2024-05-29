package com.myproject.project_oop.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "friend_request")
@Getter
public class FriendRequest extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "receiver_id")
    private Integer receiverId;

}
