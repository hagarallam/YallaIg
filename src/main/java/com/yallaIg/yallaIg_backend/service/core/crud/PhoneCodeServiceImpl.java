package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.response.PhoneCodeResponseDto;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.PhoneCodeMapper;
import com.yallaIg.yallaIg_backend.model.PhoneCode;
import com.yallaIg.yallaIg_backend.repository.PhoneCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PhoneCodeServiceImpl implements PhoneCodeService{

    private final PhoneCodeRepository phoneCodeRepository;
    private final PhoneCodeMapper phoneCodeMapper;

    @Override
    public List<PhoneCodeResponseDto> getAll() {
        List<PhoneCode> phoneCodeList = phoneCodeRepository.findAll();
        return phoneCodeList.stream().map(phoneCodeMapper::phoneCodeToPhoneCodeResponseDto).toList();
    }

    @Override
    public PhoneCode findById(Integer phoneCodeId) {
        Optional<PhoneCode> phoneCode = phoneCodeRepository.findById(phoneCodeId);
        if (phoneCode.isPresent())
            return phoneCode.get();
        throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
    }
}
