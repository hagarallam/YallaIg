package com.yallaIg.yallaIg_backend.service.auth;

import com.yallaIg.yallaIg_backend.model.EmailVerificationToken;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.repository.EmailVerificationTokenRepository;
import com.yallaIg.yallaIg_backend.service.mail.EmailService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.SUCCESS_REGISTRATION_FOR_VERIFICATION;


@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService{

    private final EmailService emailService;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Override
    public void verifyUserEmail(User user,String appUrl) {

        EmailVerificationToken emailVerificationToken = createEmailVerificationToken(user);

        emailVerificationTokenRepository.save(emailVerificationToken);

        sendVerificationMail(user.getEmail(),emailVerificationToken.getToken(),appUrl);
    }

    @Override
    public EmailVerificationToken findByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token);
    }

    @Override
    public void removeVerification(Integer id) {
        emailVerificationTokenRepository.deleteById(id);
    }

    @Override
    public void reVerifyUserEmail(User user, String appUrl) {
        EmailVerificationToken emailVerificationToken = createEmailVerificationToken(user);

        emailVerificationTokenRepository.save(emailVerificationToken);

        sendVerificationMail(user.getEmail(),emailVerificationToken.getToken(),appUrl);
    }

    private EmailVerificationToken createEmailVerificationToken(User user) {
        EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByUser(user);
        if(emailVerificationToken == null){
            emailVerificationToken = new EmailVerificationToken();
            emailVerificationToken.setUser(user);
        }
        emailVerificationToken.setToken(UUID.randomUUID().toString());
        emailVerificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        return emailVerificationToken;
    }

    private void sendVerificationMail(String userMail, String token,String appUrl) {
        String verificationBody = createVerificationBody(token,appUrl);
        emailService.sendMail(userMail,"Email Verification",verificationBody);
    }

    private String createVerificationBody(String token, String appUrl) {
        String url = appUrl + "/auth/verify-email?token=" + token;
        return SUCCESS_REGISTRATION_FOR_VERIFICATION + url;
    }



}
