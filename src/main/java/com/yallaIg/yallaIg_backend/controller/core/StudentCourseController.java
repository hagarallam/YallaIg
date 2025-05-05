package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.response.*;
import com.yallaIg.yallaIg_backend.service.core.crud.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/registered-courses")
@RequiredArgsConstructor
public class StudentCourseController {

    private final StudentCourseService studentCourseService;


    @GetMapping
    public ResponseEntity<GenericApiResponseList<StudentCourseResponseDto>> getAllCoursesByUser(@RequestParam(defaultValue = "0") Integer page,
                                                                                                @RequestParam(defaultValue = "10") Integer size){
        List<StudentCourseResponseDto> courseResponseDtos = studentCourseService.getAllCoursesByUser(page,size);
        GenericApiResponseList<StudentCourseResponseDto> genericApiResponseList = new GenericApiResponseList<>();
        genericApiResponseList.setPayload(courseResponseDtos);
        genericApiResponseList.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponseList,OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericApiResponse<StudentCourseResponseDto>> getCourseByIdByUser(@PathVariable(name = "id") Integer courseId){
        StudentCourseResponseDto courseResponseDto = studentCourseService.getCourseByIdAndUser(courseId);
        GenericApiResponse<StudentCourseResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(courseResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @GetMapping("/check-registration")
    public ResponseEntity<GenericApiResponse<Boolean>> checkIfUserRegisterdWithCourse(@RequestParam Integer courseId){
        boolean isRegistered = studentCourseService.checkIfRegisterdCourse(courseId);
        GenericApiResponse<Boolean> genericApiResponsePage = new GenericApiResponse<>();
        genericApiResponsePage.setPayload(isRegistered);
        genericApiResponsePage.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponsePage,OK);
    }
}
