package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.response.PhoneCodeResponseDto;
import com.yallaIg.yallaIg_backend.model.PhoneCode;

import java.util.List;

public interface PhoneCodeService {
    List<PhoneCodeResponseDto> getAll();

    PhoneCode findById(Integer phoneCodeId);
}
