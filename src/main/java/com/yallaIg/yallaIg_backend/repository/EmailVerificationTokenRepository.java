package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.EmailVerificationToken;
import com.yallaIg.yallaIg_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken,Integer> {
    EmailVerificationToken findByToken(String token);

    EmailVerificationToken findByUser(User user);
}
