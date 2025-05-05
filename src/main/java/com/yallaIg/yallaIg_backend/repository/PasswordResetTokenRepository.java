package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Integer> {

    Optional<PasswordResetToken> findByToken(String token);
}
