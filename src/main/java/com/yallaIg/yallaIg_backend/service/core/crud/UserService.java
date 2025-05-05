package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.UserChangePasswordRequestDto;
import com.yallaIg.yallaIg_backend.dto.request.UserRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.UserResponseDto;
import com.yallaIg.yallaIg_backend.model.User;
import org.springframework.data.domain.Page;

public interface UserService {

    User save(User user);
    boolean existsByEmail(String email);

    Page<UserResponseDto> getAllUsers(int page, int size);

    Page<UserResponseDto> getAllUsersWithWallet(int page, int size);

    UserResponseDto findById(Integer id);

    UserResponseDto getLoggedInUserAccountData();

    User findByUserId(Integer userId);

    void updateUser( UserRequestDto userRequestDto);

    void deleteUser(Integer id);

    User findByEmail(String email);

    void createPasswordResetTokenForUser(User user,String appUrl);

    void updateUserPassword(User user, String newPassword);

    void changeUserPassword(UserChangePasswordRequestDto changePasswordRequestDto);

    void updateUser(User user);
}

