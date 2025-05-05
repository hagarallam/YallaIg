package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.CourseRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.CourseResponseDto;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.CourseMapper;
import com.yallaIg.yallaIg_backend.mapper.FileMapper;
import com.yallaIg.yallaIg_backend.model.Course;
import com.yallaIg.yallaIg_backend.model.Instructor;
import com.yallaIg.yallaIg_backend.repository.CourseRepository;
import com.yallaIg.yallaIg_backend.repository.ExamSessionRepository;
import com.yallaIg.yallaIg_backend.repository.LevelRepository;
import com.yallaIg.yallaIg_backend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final InstructorService instructorService;
    private final CourseMapper courseMapper;
    private final FileMapper fileMapper;
    private final ExamSessionRepository examSessionRepository;
    private final SubjectRepository subjectRepository;
    private final LevelRepository levelRepository;


    @Override
    public List<CourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(courseMapper::courseToCourseResponseDto).toList();
    }

    @Override
    public CourseResponseDto findById(Integer id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(courseMapper::courseToCourseResponseDto).orElseThrow(() -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
    }

    @Override
    public Integer createCourse(CourseRequestDto courseRequestDto) {
        Course course = courseMapper.courserRequestDtoToCourse(courseRequestDto);

        course.setExamSession(examSessionRepository.findByExamSessionValue(courseRequestDto.getExamSessionEnum()).get());
        course.setSubject(subjectRepository.findBySubjectEnum(courseRequestDto.getSubjectEnum()).get());
        course.setLevel(levelRepository.findByLevelEnum(courseRequestDto.getLevelEnum()).get());

        // handle many-to-many relationship
        mapInstructorsToTheCourse(course, courseRequestDto);

        return courseRepository.save(course).getCourseId();
    }


    @Override
    public void updateCourse(Integer id, CourseRequestDto courseRequestDto) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            courseRepository.save(updatedCourseData(course.get(), courseRequestDto));
            return;
        }
        throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
    }

    private Course updatedCourseData(Course course, CourseRequestDto courseRequestDto) {
        course.setName(courseRequestDto.getName());
        course.setPrice(courseRequestDto.getPrice());
        course.setLink(courseRequestDto.getLink());
        course.setStartDate(courseRequestDto.getStartDate());
        course.setEndDate(courseRequestDto.getEndDate());
        course.setDescription(courseRequestDto.getDescription());
        course.setExamSession(examSessionRepository.findByExamSessionValue(courseRequestDto.getExamSessionEnum()).get());
        course.setSubject(subjectRepository.findBySubjectEnum(courseRequestDto.getSubjectEnum()).get());
        course.setLevel(levelRepository.findByLevelEnum(courseRequestDto.getLevelEnum()).get());
        course.setCourseFile(fileMapper.multipartFileToFile(courseRequestDto.getCourseImage()));

        //map course and instructor
        mapInstructorsToTheCourse(course, courseRequestDto);
        course.setLastModificationDate((new Date()));
        return course;
    }

    public void mapInstructorsToTheCourse(Course course, CourseRequestDto courseRequestDto) {
        List<Instructor> instructorList = instructorService.findAllInstructorByIds(courseRequestDto.getInstructorIds());
        course.setInstructors(instructorList);

        // add Courses to the instructor
        for (Instructor instructor : instructorList) {
            List<Course> courses = instructor.getCourses();
            if (courses == null) {
                courses = new ArrayList<>();
                instructor.setCourses(courses);
            }
            if (!courses.contains(course)) {
                courses.add(course);
            }
        }
    }


    @Override
    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }
}
