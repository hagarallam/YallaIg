package com.yallaIg.yallaIg_backend.service.core.enrollment;

import com.yallaIg.yallaIg_backend.dto.request.ResourceRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.ResourceRegistrationResponseDto;

public interface ResourceEnrollmentService {

    ResourceRegistrationResponseDto registerResource(ResourceRegistrationRequestDto resourceRegistrationRequestDto);

}
