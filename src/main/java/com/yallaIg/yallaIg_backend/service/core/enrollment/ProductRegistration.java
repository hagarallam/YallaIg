package com.yallaIg.yallaIg_backend.service.core.enrollment;

import com.yallaIg.yallaIg_backend.dto.request.ProductRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.ProductRegistrationResponseDto;

public interface ProductRegistration {
    ProductRegistrationResponseDto register(ProductRegistrationRequestDto productRegistrationRequestDto);
}
