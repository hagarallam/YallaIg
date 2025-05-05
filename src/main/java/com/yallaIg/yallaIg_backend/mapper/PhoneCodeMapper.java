package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.response.PhoneCodeResponseDto;
import com.yallaIg.yallaIg_backend.model.PhoneCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneCodeMapper {


    @Mapping(target = "countryId",source = "country.countryId")
    PhoneCodeResponseDto phoneCodeToPhoneCodeResponseDto(PhoneCode phoneCode);
}
