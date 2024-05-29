package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Integer> {

    Optional<EmailVerificationToken> findByToken(String token);

    @Query(
            value = "select e_t from EmailVerificationToken e_t inner join User u on e_t.user.id = u.id where u.id = :id and(e_t.expired = 0)"
    )
    List<EmailVerificationToken> findAllValidTokenByUser(Integer id);

}
