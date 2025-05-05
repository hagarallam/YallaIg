package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.ProductRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.response.ProductRegistrationResponseDto;
import com.yallaIg.yallaIg_backend.service.core.enrollment.ProductRegistration;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.SUCCESS_PRODUCT_REGISTRATION;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class ProductRegistrationController {

    private final ProductRegistration productRegistration;

    @PostMapping
    public ResponseEntity<GenericApiResponse<ProductRegistrationResponseDto>> register(@RequestBody @Valid ProductRegistrationRequestDto productRegistrationRequestDto){
        ProductRegistrationResponseDto productRegistrationResponseDto = productRegistration.register(productRegistrationRequestDto);
        GenericApiResponse<ProductRegistrationResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(productRegistrationResponseDto);
        genericApiResponse.setMessage(SUCCESS_PRODUCT_REGISTRATION);
        genericApiResponse.setStatusCode(CREATED.value());
        return new ResponseEntity<>(genericApiResponse,CREATED);
    }
}
