package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Integer> {

    Optional<AuthToken> findByToken(String token);

    @Query(
            value = "select a_t from AuthToken a_t inner join User u on a_t.user.id = u.id where u.id = :id and (a_t.expired = 0 or a_t.revoked = 0)"
    )
    List<AuthToken> findAllValidTokenByUser(Integer id);

}
