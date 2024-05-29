package com.myproject.project_oop.model;

import com.myproject.project_oop.model.constant.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseModel{

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Column(name = "about", columnDefinition = "TEXT")
    private String about;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File avatar;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

}
