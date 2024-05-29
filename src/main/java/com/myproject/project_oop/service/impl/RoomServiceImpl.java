package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.Participant;
import com.myproject.project_oop.model.Room;
import com.myproject.project_oop.repository.RoomRepository;
import com.myproject.project_oop.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room getRoom(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public List<Integer> getParticipantsId(Integer roomId) {
        return roomRepository.getParticipantsByRoomId(roomId).stream()
                .map(participant -> participant.getUser().getId())
                .collect(Collectors.toList());
    }
}
