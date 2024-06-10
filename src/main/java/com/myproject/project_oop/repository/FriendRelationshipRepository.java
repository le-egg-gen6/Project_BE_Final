package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Integer> {

    @Query(
            value = "select fr.friend from FriendRelationship fr where fr.user.id = :id"
    )
    List<Integer> getAllFriendId(Integer id);

}
