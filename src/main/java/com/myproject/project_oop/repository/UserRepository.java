package com.myproject.project_oop.repository;

import com.myproject.project_oop.model.User;
import com.myproject.project_oop.model.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(
            value = "select u from User u join EmailVerificationToken e_t on u.id = e_t.user.id where u.id = :id and e_t.token = :token and e_t.expired = 0"
    )
    Optional<User> findByIdAndEmailVerificationToken(Integer id, String token);

    @Query(
            value = "select u from User u join PasswordResetToken p_t on u.id = p_t.user.id where u.id = :id and p_t.token = :token and p_t.expired = 0"
    )
    Optional<User> findByIdAndPasswordResetToken(Integer id, String token);

    List<User> findAllByStatus(Status status);

}
