package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.response.CountryResponseDto;
import com.yallaIg.yallaIg_backend.model.Country;

import java.util.List;

public interface CountryService {
    List<CountryResponseDto> getAll();

    Country findById(Integer countryId);
}
