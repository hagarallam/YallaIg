package com.yallaIg.yallaIg_backend.service.auth;

import com.yallaIg.yallaIg_backend.model.PasswordResetToken;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.VariableConstants.PASSWORD_RESET_TOKEN_EXPIRATION;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void createPasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(PASSWORD_RESET_TOKEN_EXPIRATION ));
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);
        return passwordResetToken.orElse(null);
    }

    @Override
    public void deleteToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }
}
