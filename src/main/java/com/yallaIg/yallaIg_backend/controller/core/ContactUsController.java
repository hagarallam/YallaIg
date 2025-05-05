package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.ContactUsRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.service.core.crud.ContractUsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.SUCCESS_CONTACT_US_SUBMISSION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/contact-us")
@RequiredArgsConstructor
public class ContactUsController {

    private final ContractUsService contractUsService;

    @PostMapping
    public ResponseEntity<GenericApiResponse> contactUs(@RequestBody ContactUsRequestDto contactUsRequestDto){
        contractUsService.contactUs(contactUsRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse();
        genericApiResponse.setMessage(SUCCESS_CONTACT_US_SUBMISSION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }
}
