package com.yallaIg.yallaIg_backend.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{


    @Value("${spring.mail.username}")
    public String FROM;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(String to, String subject, String body) {
        // build mail object
        SimpleMailMessage message = getSimpleMailMessage(to, subject, body);

        javaMailSender.send(message);
    }

    private SimpleMailMessage getSimpleMailMessage(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        return message;
    }


    @Override
    public void sendMailWithAttachment(String to, String subject, String body,String attachmentFilename, InputStreamSource inputStreamSource) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setFrom(FROM);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);

        mimeMessageHelper.addAttachment(attachmentFilename, inputStreamSource);

        javaMailSender.send(mimeMessage);
    }
}
