package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.UserChangePasswordRequestDto;
import com.yallaIg.yallaIg_backend.dto.request.UserRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.*;
import com.yallaIg.yallaIg_backend.service.core.crud.ResourceService;
import com.yallaIg.yallaIg_backend.service.core.crud.StudentResourceService;
import com.yallaIg.yallaIg_backend.service.core.crud.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class UserDashboardController {

    private final UserService userService;
    private final ResourceService resourceService;
    private final StudentResourceService studentResourceService;

    @GetMapping("/my-account")
    public ResponseEntity<GenericApiResponse<UserResponseDto>> getUserAccountInfo(){
        UserResponseDto userResponseDto = userService.getLoggedInUserAccountData();
        GenericApiResponse<UserResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(userResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @GetMapping("/my-resources")
    public ResponseEntity<GenericApiResponsePage<StudentResourceResponseDto>> getUserResources(@RequestParam(defaultValue = "0") Integer page,
                                                                                               @RequestParam(defaultValue = "10") Integer size){
        Page<StudentResourceResponseDto> resourcesByUser = studentResourceService.getAllResourcesByUser(page, size);
        GenericApiResponsePage<StudentResourceResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(resourcesByUser);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @GetMapping("/my-resources/{id}")
    public ResponseEntity<GenericApiResponse<StudentResourceResponseDto>> getUserResourcesById(@PathVariable(name = "id") Integer resourceId ){
        StudentResourceResponseDto resourcesByUser = studentResourceService.getRegistredResourceById(resourceId);
        GenericApiResponse<StudentResourceResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(resourcesByUser);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }



    @PutMapping("/my-account")
    public ResponseEntity<GenericApiResponse> updateUser(@Valid @ModelAttribute UserRequestDto userRequestDto){
        userService.updateUser(userRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_USER_UPDATION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @PutMapping("/my-account/password")
    public ResponseEntity<GenericApiResponse> changeUserPassword(@Valid @RequestBody UserChangePasswordRequestDto changePasswordRequestDto){
        userService.changeUserPassword(changePasswordRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_RESET_PASSWORD);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

}
