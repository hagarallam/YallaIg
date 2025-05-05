package com.yallaIg.yallaIg_backend.service.mail;

import jakarta.mail.MessagingException;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

public interface EmailService {


    void sendMail(String to,String subject,String body);

    void sendMailWithAttachment(String to,String subject,String body,String attachmentFilename, InputStreamSource inputStreamSource) throws MessagingException;

}
