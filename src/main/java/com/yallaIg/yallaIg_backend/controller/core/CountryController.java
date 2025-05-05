package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.response.CountryResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponseList;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponsePage;
import com.yallaIg.yallaIg_backend.service.core.crud.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;


    @GetMapping
    public ResponseEntity<GenericApiResponseList<CountryResponseDto>> getAll(){
        List<CountryResponseDto> countryResponseDtos = countryService.getAll();
        GenericApiResponseList<CountryResponseDto> genericApiResponseList = new GenericApiResponseList<>();
        genericApiResponseList.setPayload(countryResponseDtos);
        genericApiResponseList.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponseList,OK);

    }
}
