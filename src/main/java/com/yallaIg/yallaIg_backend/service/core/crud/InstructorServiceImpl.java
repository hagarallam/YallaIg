package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.InstructorRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.InstructorResponseDto;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.FileMapper;
import com.yallaIg.yallaIg_backend.mapper.InstructorMapper;
import com.yallaIg.yallaIg_backend.model.Instructor;
import com.yallaIg.yallaIg_backend.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final FileMapper fileMapper;

    @Override
    public Page<InstructorResponseDto> getAllInstructors(int page , int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Instructor> instructors =  instructorRepository.findAll(pageable);
        return instructors.map(instructorMapper::instructorToInstructorResponseDto);
    }
    @Override
    public InstructorResponseDto findInstructorById(Integer id) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        return instructor.map(instructorMapper::instructorToInstructorResponseDto).orElseThrow(()-> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
    }

    @Override
    public List<Instructor> findAllInstructorByIds(List<Integer> ids) {
        return instructorRepository.findAllById(ids);
    }

    @Override
    public Integer addInstructor(InstructorRequestDto instructorRequestDto) {
        Instructor instructor = instructorMapper.instructorRequestDtoToInstructor(instructorRequestDto);
        return instructorRepository.save(instructor).getInstructorId();
    }

    @Override
    public void updateInstructor(Integer id, InstructorRequestDto instructorRequestDto) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        if(instructor.isPresent()){
            instructorRepository.save(updatedInstructorData(instructor.get(),instructorRequestDto));
            return;
        }
        throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);

    }

    private Instructor updatedInstructorData(Instructor instructor, InstructorRequestDto instructorRequestDto) {
        instructor.setFirstName(instructorRequestDto.getFirstName());
        instructor.setLastName(instructorRequestDto.getLastName());
        instructor.setBio(instructorRequestDto.getBio());
        instructor.setInstructorImage(fileMapper.multipartFileToFile(instructorRequestDto.getInstructorImage()));
        return instructor;
    }


    @Override
    public void deleteInstructor(Integer id) {
        instructorRepository.deleteById(id);
    }

}
