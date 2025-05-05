package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponseList;
import com.yallaIg.yallaIg_backend.dto.response.PhoneCodeResponseDto;
import com.yallaIg.yallaIg_backend.service.core.crud.PhoneCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/phone-codes")
@RequiredArgsConstructor
public class PhoneCodeController {

    private final PhoneCodeService phoneCodeService;

    @GetMapping
    public ResponseEntity<GenericApiResponseList<PhoneCodeResponseDto>> getAll(){
        List<PhoneCodeResponseDto> phoneCodeResponseDtos = phoneCodeService.getAll();
        GenericApiResponseList<PhoneCodeResponseDto> genericApiResponseList = new GenericApiResponseList<>();
        genericApiResponseList.setPayload(phoneCodeResponseDtos);
        genericApiResponseList.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponseList,OK);

    }


}
