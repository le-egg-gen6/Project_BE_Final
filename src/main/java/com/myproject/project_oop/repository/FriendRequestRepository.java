package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    Optional<FriendRequest> findById(Integer id);

    @Query(
            value = "select f_r from FriendRequest f_r where f_r.receiverId = :id"
    )
    List<FriendRequest> findAllFriendRequestByReceiverId(Integer id);

    Optional<FriendRequest> findByUserIdAndReceiverId(Integer userId, Integer receiverId);

}
