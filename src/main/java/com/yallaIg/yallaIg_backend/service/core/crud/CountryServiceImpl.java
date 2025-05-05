package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.response.CountryResponseDto;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.CountryMapper;
import com.yallaIg.yallaIg_backend.model.Country;
import com.yallaIg.yallaIg_backend.repository.CountryRepository;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService{

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public List<CountryResponseDto> getAll() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream().map(countryMapper::countryToCountryResponseDto).toList();
    }

    @Override
    public Country findById(Integer countryId) {
        Optional<Country> country = countryRepository.findById(countryId);
        if(country.isPresent())
            return country.get();
        throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
    }
}
