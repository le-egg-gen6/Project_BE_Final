package com.myproject.project_oop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "room")
public class Room extends BaseModel {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "room")
    private Set<Participant> participants;

}
