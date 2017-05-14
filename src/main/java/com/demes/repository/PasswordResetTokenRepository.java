package com.demes.repository;
import com.demes.entity.PasswordResetToken;
import com.demes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    void deleteByToken(String token);

    void deleteByUser(User user);
}
