package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    Optional<PasswordResetToken> findByToken(String token);

    @Query(
            value = "select p_t from PasswordResetToken p_t inner join User u on p_t.user.id = u.id where u.id = :id and(p_t.expired = 0)"
    )
    List<PasswordResetToken> findAllValidTokenByUser(Integer id);

}
