package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.Participant;
import com.myproject.project_oop.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findById(Integer id);

    @Query(
        value = "select p from Participant p where p.room.id = :id"
    )
    List<Participant> getParticipantsByRoomId(Integer id);

}
