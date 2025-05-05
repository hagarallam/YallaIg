package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.TeacherApplicationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponsePage;
import com.yallaIg.yallaIg_backend.dto.response.TeacherApplicationResponseDto;
import com.yallaIg.yallaIg_backend.service.core.crud.TeacherApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.SUCCESS_TEACH_WITH_US_SUBMISSION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeacherApplicationController {

    private final TeacherApplicationService teacherApplicationService;

    @PostMapping("/apply-to-teach")
    public ResponseEntity<GenericApiResponse> applyToTeach(@ModelAttribute @Valid TeacherApplicationRequestDto applicationRequestDto){
        teacherApplicationService.submitApplication(applicationRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse();
        genericApiResponse.setMessage(SUCCESS_TEACH_WITH_US_SUBMISSION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }


    @GetMapping("/teachers-applications")
    public ResponseEntity<GenericApiResponsePage<TeacherApplicationResponseDto>> getAllTeachersApplications(@RequestParam(defaultValue = "0") Integer page,
                                                                                                            @RequestParam(defaultValue = "10") Integer size){
        Page<TeacherApplicationResponseDto> teacherApplicationResponseDtos =  teacherApplicationService.getAll(page,size);
        GenericApiResponsePage<TeacherApplicationResponseDto> genericApiResponsePage = new GenericApiResponsePage();
        genericApiResponsePage.setPayload(teacherApplicationResponseDtos);
        genericApiResponsePage.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponsePage,OK);
    }

}
