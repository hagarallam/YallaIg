package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.ContactUsRequestDto;
import com.yallaIg.yallaIg_backend.service.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractUsServiceImpl implements ContractUsService{

    @Value("${admin.mail}")
    private String adminMail;

    private final EmailService emailService;

    @Override
    public void contactUs(ContactUsRequestDto contactUsRequestDto) {
        String body = createMailBody(contactUsRequestDto);
        sendMail(body);
    }


    private String createMailBody(ContactUsRequestDto contactUsRequestDto) {
        return "Hello Admins !\n" +
                "There is a new Problem submitted from '" + contactUsRequestDto.getName() + "'" +
                "\nHis Description is : " + contactUsRequestDto.getDescription() +
                "\nHis Phone is " + contactUsRequestDto.getPhone() +
                "\nThanks !"
                ;
    }

    private void sendMail(String body) {
        emailService.sendMail(adminMail,"New Contact Us Form",body);
    }

}
