package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.ResourceRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.ResourceResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.StudentResourceResponseDto;
import com.yallaIg.yallaIg_backend.model.Resource;
import org.springframework.data.domain.Page;

public interface StudentResourceService {
    Resource register(ResourceRegistrationRequestDto resourceRegistrationRequestDto, Integer orderId);

    Page<StudentResourceResponseDto> getAllResourcesByUser(int page, int size);

    boolean checkIfResourceRegisteredByUser(Integer resourceId);

    StudentResourceResponseDto getRegistredResourceById(Integer id);

}
