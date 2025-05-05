package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.response.CountryResponseDto;
import com.yallaIg.yallaIg_backend.model.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryResponseDto countryToCountryResponseDto(Country country);
}
