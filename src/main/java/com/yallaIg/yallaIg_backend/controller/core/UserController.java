package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.UserRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponsePage;
import com.yallaIg.yallaIg_backend.dto.response.UserResponseDto;
import com.yallaIg.yallaIg_backend.service.core.crud.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<GenericApiResponsePage<UserResponseDto>> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "10") Integer size){
        Page<UserResponseDto> userResponseDtos = userService.getAllUsersWithWallet(page,size);
        GenericApiResponsePage<UserResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(userResponseDtos);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericApiResponse<UserResponseDto>> getUserById(@PathVariable Integer id){
        UserResponseDto userResponseDto = userService.findById(id);
        GenericApiResponse<UserResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(userResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericApiResponse> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_USER_DELETION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }
}
