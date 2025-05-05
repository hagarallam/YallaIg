package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.UserChangePasswordRequestDto;
import com.yallaIg.yallaIg_backend.dto.request.UserRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.UserResponseDto;
import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.exception.GeneralValidationException;
import com.yallaIg.yallaIg_backend.mapper.FileMapper;
import com.yallaIg.yallaIg_backend.mapper.UserMapper;
import com.yallaIg.yallaIg_backend.model.*;
import com.yallaIg.yallaIg_backend.repository.UserRepository;
import com.yallaIg.yallaIg_backend.repository.dto.UserDataDto;
import com.yallaIg.yallaIg_backend.service.auth.PasswordResetTokenService;
import com.yallaIg.yallaIg_backend.service.mail.EmailService;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.*;
import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Value("${front-port}")
    private String frontPort;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileMapper fileMapper;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final CountryService countryService;
    private final PhoneCodeService phoneCodeService;
    private final UserWalletService userWalletService;
    private final ReactionService reactionService;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }



    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserResponseDto> getAllUsers(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page, size);
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::userToUserResponseDto);
    }

    @Override
    public Page<UserResponseDto> getAllUsersWithWallet(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page, size);
        Page<UserDataDto> userDataDtos = userRepository.findAllWithWallet(pageable);
        return userDataDtos.map(userMapper::userToUserResponseDto);
    }

    @Override
    public UserResponseDto findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user.get());
            setUserWalletBalance(userResponseDto);
            return userResponseDto;
        }
        throw new ItemNotFoundException(ERROR_USER_NOT_FOUND);
    }

    @Override
    public UserResponseDto getLoggedInUserAccountData() {
        User user = findByUserId(SecurityUtil.getCurrentUserId());
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user);
        setAdditionalUserData(userResponseDto);
        return userResponseDto;
    }

    private void setAdditionalUserData( UserResponseDto userResponseDto) {
        setUserCommunityData(userResponseDto);
        setUserWalletBalance(userResponseDto);
    }

    private void setUserCommunityData(UserResponseDto userResponseDto) {
        List<Integer> likedPostsIds = reactionService.getReactablesIdByReactableTypeAndUserId(
                ReactableType.POST,userResponseDto.getUserId(), ReactionType.USEFUL );
        userResponseDto.setLikedPostIds(likedPostsIds);
    }

    private void setUserWalletBalance(UserResponseDto userResponseDto) {
        UserWallet userWallet = userWalletService.getUserWallet(userResponseDto.getUserId());
        userResponseDto.setUserBalance(userWallet.getBalance());
    }

    @Override
    public User findByUserId(Integer userId) {
        return userRepository.findByUserId(userId).orElseThrow(()-> new ItemNotFoundException(ERROR_USER_NOT_FOUND));
    }

    @Override
    public void updateUser(UserRequestDto userRequestDto) {
        User user = findByUserId(SecurityUtil.getCurrentUserId());
        userRepository.save(updateUserData(user,userRequestDto));
    }

    private User updateUserData(User user, UserRequestDto userRequestDto) {
        user.setFullName(userRequestDto.getFullName());
        user.setUserImage(fileMapper.multipartFileToFile(userRequestDto.getUserImage()));

        //update user info data
        if(Objects.nonNull(user.getContactInfo())){
            updateContactInfoData(user.getContactInfo(),userRequestDto);
        }
        return user;
    }

    private void updateContactInfoData(UserContactInfo userContactInfo, UserRequestDto userRequestDto) {
        userContactInfo.setPhone(userRequestDto.getPhone());

        Country country = countryService.findById(userRequestDto.getCountryId());
        userContactInfo.setCountry(country);

        PhoneCode phoneCode = phoneCodeService.findById(userRequestDto.getPhoneCodeId());
        userContactInfo.setPhoneCode(phoneCode);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public void createPasswordResetTokenForUser(User user,String appUrl) {
        String token = UUID.randomUUID().toString();

        createPasswordResetTokenObject(user,token);

        sendMailToTheUser(user.getEmail(),appUrl,token);
    }


    private void createPasswordResetTokenObject(User user,String token) {
        passwordResetTokenService.createPasswordResetToken(user,token);
    }

    private void sendMailToTheUser(String email,String appUrl, String token) {
        String body = createResetPasswordMailBody(appUrl,token);
        emailService.sendMail(email,"Reset Your Password",body);
    }

    private String createResetPasswordMailBody(String appUrl , String token) {
        String url = appUrl+ frontPort +"/auth/reset-password?token="+token;
        return SUCCESS_RESET_PASSWORD_EMAIL_MESSAGE + url;
    }

    @Override
    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void changeUserPassword(UserChangePasswordRequestDto changePasswordRequestDto) {
        User user = findByUserId(SecurityUtil.getCurrentUserId());
        String userPassword = user.getPassword();

        if(!isCurrentPasswordCorrect(changePasswordRequestDto.getCurrentPassword(),userPassword)){
            throw new GeneralValidationException(ERROR_PASSWORD_INCORRECT);
        }
        if(isNewPasswordSameAsOld(changePasswordRequestDto.getNewPassword(),userPassword)){
            throw new GeneralValidationException(ERROR_NEW_PASSWORD_IS_SAME);
        }

        updateUserPassword(user,changePasswordRequestDto.getNewPassword());
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }


    private boolean isCurrentPasswordCorrect( String enteredPassword, String actualPassword) {
        return isPasswordsMatches(enteredPassword, actualPassword);
    }

    private boolean isNewPasswordSameAsOld( String newPassword ,String oldPassword ) {
        return isPasswordsMatches(newPassword, oldPassword);
    }


    private boolean isPasswordsMatches(String rawPassword,String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
