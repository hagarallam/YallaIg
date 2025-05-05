package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.request.RegisterRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.CommunityUserDto;
import com.yallaIg.yallaIg_backend.dto.response.UserResponseDto;
import com.yallaIg.yallaIg_backend.model.Role;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.model.UserContactInfo;
import com.yallaIg.yallaIg_backend.repository.dto.UserDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring",uses = {FileMapper.class})
public interface UserMapper {

    @Mapping(target = "userId",ignore = true)
    User registerDtoToUser(RegisterRequestDto registerRequestDto);

    //@Mapping(target = "roleId",expression = "java(mapRoleIds(user.getRoles()))")
    @Mapping(target = "phone",source = "contactInfo.phone")
    @Mapping(target = "countryId",source = "contactInfo.country.countryId")
    @Mapping(target = "phoneCodeId",source = "contactInfo.phoneCode.phoneCodeId")
    UserResponseDto userToUserResponseDto(User user);

    UserResponseDto userToUserResponseDto(UserDataDto userDataDto);

    CommunityUserDto userToCommunityUserDto(User user);

//    default Integer mapRoleIds(List<Role> roles) {
//        return Objects.nonNull(roles) && !roles.isEmpty() ? roles.getFirst().getRoleId() : null;
//    }

    UserContactInfo registerDtoToUserContactInfo(RegisterRequestDto registerRequestDto);


}
