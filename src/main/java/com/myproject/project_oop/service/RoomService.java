package com.myproject.project_oop.service;

import com.myproject.project_oop.model.Room;

import java.util.List;

public interface RoomService {

    Room getRoom(Integer id);

    List<Integer> getParticipantsId(Integer roomId);

}
