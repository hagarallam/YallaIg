package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.InstructorRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.InstructorResponseDto;
import com.yallaIg.yallaIg_backend.model.Instructor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstructorService {
    Page<InstructorResponseDto> getAllInstructors(int page , int size) ;

    InstructorResponseDto findInstructorById(Integer id);

    List<Instructor> findAllInstructorByIds(List<Integer> ids);

    Integer addInstructor(InstructorRequestDto instructorRequestDto);

    void deleteInstructor(Integer id);

    void updateInstructor(Integer id, InstructorRequestDto instructorRequestDto);
}
