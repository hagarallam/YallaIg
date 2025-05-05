package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.CourseRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.CourseResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponseList;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponsePage;
import com.yallaIg.yallaIg_backend.service.core.crud.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<GenericApiResponseList<CourseResponseDto>> getAllCourses(){
        List<CourseResponseDto> courseResponseDtos = courseService.getAllCourses();
        GenericApiResponseList<CourseResponseDto> genericApiResponse = new GenericApiResponseList<>();
        genericApiResponse.setPayload(courseResponseDtos);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericApiResponse<CourseResponseDto>> getCourseById(@PathVariable Integer id){
        CourseResponseDto courseResponseDto = courseService.findById(id);
        GenericApiResponse<CourseResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(courseResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }


    @PostMapping
    public ResponseEntity<GenericApiResponse<Integer>> createCourse(@Valid @ModelAttribute CourseRequestDto courseRequestDto){
        Integer id = courseService.createCourse(courseRequestDto);
        GenericApiResponse<Integer> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(id);
        genericApiResponse.setMessage(SUCCESS_COURSE_CREATION);
        genericApiResponse.setStatusCode(CREATED.value());
        return new ResponseEntity<>(genericApiResponse, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericApiResponse> updateCourse(@PathVariable Integer id,
                                                                    @Valid @ModelAttribute CourseRequestDto courseRequestDto){
        courseService.updateCourse(id,courseRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_COURSE_UPDATION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericApiResponse> deleteCourse(@PathVariable Integer id){
        courseService.deleteCourse(id);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_COURSE_DELETION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }
}
