package com.myproject.project_oop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "email_verification_token",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "token")
        },
        indexes = {
                @Index(name = "token_index", columnList = "token")
        }

)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerificationToken extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token")
    private String token;

    @Column(name = "expired")
    private Integer expired;

}