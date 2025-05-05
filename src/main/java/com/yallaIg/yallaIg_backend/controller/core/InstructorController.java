package com.yallaIg.yallaIg_backend.controller.core;
import com.yallaIg.yallaIg_backend.dto.request.InstructorRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponsePage;
import com.yallaIg.yallaIg_backend.dto.response.InstructorResponseDto;
import com.yallaIg.yallaIg_backend.service.core.crud.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping
    public ResponseEntity<GenericApiResponsePage<InstructorResponseDto>> getAllInstructors(@RequestParam(defaultValue = "0") Integer page,
                                                                                           @RequestParam(defaultValue = "10") Integer size){
        Page<InstructorResponseDto> instructorResponseDtos = instructorService.getAllInstructors(page,size);
        GenericApiResponsePage<InstructorResponseDto> genericApiResponsePage = new GenericApiResponsePage<>();
        genericApiResponsePage.setPayload(instructorResponseDtos);
        genericApiResponsePage.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponsePage,OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericApiResponse<InstructorResponseDto>> getInstructor(@PathVariable Integer id){
        InstructorResponseDto instructorResponseDto = instructorService.findInstructorById(id);
        GenericApiResponse<InstructorResponseDto> genericApiResponseList = new GenericApiResponse<>();
        genericApiResponseList.setPayload(instructorResponseDto);
        genericApiResponseList.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponseList,OK);
    }

    @PostMapping
    public ResponseEntity<GenericApiResponse<Integer>> addInstructor(@Valid @ModelAttribute InstructorRequestDto instructorRequestDto){
        Integer id = instructorService.addInstructor(instructorRequestDto);
        GenericApiResponse<Integer> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(id);
        genericApiResponse.setMessage(SUCCESS_INSTRUCTOR_CREATION);
        genericApiResponse.setStatusCode(CREATED.value());
        return new ResponseEntity<>(genericApiResponse, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericApiResponse> updateInstructor(@PathVariable Integer id,
                                                           @Valid @ModelAttribute InstructorRequestDto instructorRequestDto){
        instructorService.updateInstructor(id,instructorRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_INSTRUCTOR_UPDATION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericApiResponse> deleteInstructor(@PathVariable Integer id){
        instructorService.deleteInstructor(id);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_INSTRUCTOR_DELETION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

}
