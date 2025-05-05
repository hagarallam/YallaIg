package com.yallaIg.yallaIg_backend.service.auth;
import com.yallaIg.yallaIg_backend.dto.request.RegisterRequestDto;
import com.yallaIg.yallaIg_backend.enums.RoleEnum;
import com.yallaIg.yallaIg_backend.enums.StatusEnum;
import com.yallaIg.yallaIg_backend.mapper.UserMapper;
import com.yallaIg.yallaIg_backend.model.*;
import com.yallaIg.yallaIg_backend.repository.StatusRepository;
import com.yallaIg.yallaIg_backend.service.core.crud.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterServiceImpl implements RegistrationService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserWalletService userWalletService;
    private final RoleService roleService;
    private final CountryService countryService;
    private final PhoneCodeService phoneCodeService;
    private final StatusRepository statusRepository;
    private final EmailVerificationService emailVerificationService;

    @Override
    public Integer createUserWithAllData(RegisterRequestDto registerRequestDto,String appUrl) {
        // create user and user data objects
        User user = createUser(registerRequestDto);

        emailVerificationService.verifyUserEmail(user,appUrl);

        return user.getUserId();
    }


    @Override
    public User createUser(RegisterRequestDto registerRequestDto) {
        User user = userMapper.registerDtoToUser(registerRequestDto);
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        // Get role Object from db
        Role roles = getRuleByRuleName(RoleEnum.STUDENT);
        user.setRoles(Collections.singletonList(roles));

        Status userStatus = getStatus(StatusEnum.ACTIVE);
        user.setStatus(userStatus);

        UserContactInfo userContactInfo = getUserContactInfo(registerRequestDto);
        userContactInfo.setUser(user);
        user.setContactInfo(userContactInfo);

        return userService.save(user);
    }



    public UserContactInfo getUserContactInfo(RegisterRequestDto registerRequestDto) {
        UserContactInfo userContactInfo = userMapper.registerDtoToUserContactInfo(registerRequestDto);

        Country country = countryService.findById(registerRequestDto.getCountryId());
        userContactInfo.setCountry(country);

        PhoneCode phoneCode = phoneCodeService.findById(registerRequestDto.getPhoneCodeId());
        userContactInfo.setPhoneCode(phoneCode);

        return userContactInfo;
    }

    private Role getRuleByRuleName(RoleEnum roleEnum) {
        return roleService.getRoleByRoleName(roleEnum).get();
    }

    private Status getStatus(StatusEnum statusEnum) {
        return statusRepository.findByStatusEnum(statusEnum).get();
    }

    @Override
    public boolean isEmailExists(String email) {
        return userService.existsByEmail(email);
    }


    @Override
    public void updateUserAfterVerification(EmailVerificationToken emailVerificationToken) {
        User user = emailVerificationToken.getUser();

        updateUserVerificationStatus(user);

        // create wallet
        createUserWallet(user);

        emailVerificationService.removeVerification(emailVerificationToken.getId());
    }

    private void updateUserVerificationStatus(User user) {
        user.setMailVerified(true);
        userService.updateUser(user);
    }

    public void createUserWallet(User user) {
        userWalletService.createUserWallet(user);
    }

}
