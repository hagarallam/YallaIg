package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.dto.request.TeacherApplicationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.TeacherApplicationResponseDto;
import com.yallaIg.yallaIg_backend.exception.BusinessValidationException;
import com.yallaIg.yallaIg_backend.exception.GeneralValidationException;
import com.yallaIg.yallaIg_backend.mapper.TeacherApplicationMapper;
import com.yallaIg.yallaIg_backend.model.TeacherApplication;
import com.yallaIg.yallaIg_backend.repository.TeacherApplicationRepository;
import com.yallaIg.yallaIg_backend.service.mail.EmailService;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeacherApplicationServiceImpl implements TeacherApplicationService {

    private final TeacherApplicationRepository teacherApplicationRepository;
    private final TeacherApplicationMapper teacherApplicationMapper;
    private final EmailService emailService;

    @Override
    public void submitApplication(TeacherApplicationRequestDto applicationRequestDto) {
        if(isDuplicateApplication(applicationRequestDto.getEmail())){
            throw new BusinessValidationException(ErrorConstants.ERROR_MULTIPLE_TEACH_APPLICATION);
        }
        TeacherApplication teacherApplication = teacherApplicationMapper.requestDtoToTeacherApplication(applicationRequestDto);
        teacherApplicationRepository.save(teacherApplication);

        sendMail(teacherApplication.getEmail(), getBody(teacherApplication),applicationRequestDto.getCvFile());
    }

    private boolean isDuplicateApplication(String email) {
        return Objects.nonNull(teacherApplicationRepository.findByEmail(email)) ;
    }

    private static String getBody(TeacherApplication teacherApplication) {
        return "Hello Admins !\n" +
                "There is a new application submitted from " + teacherApplication.getName() + "\n" +
                "His specialization is " + teacherApplication.getSpecialization()+"\n"+
                "Please check the reports !\n Thanks !";
    }

    private void sendMail(String to, String body, MultipartFile multipartFile) {
        try{
            emailService.sendMailWithAttachment(to, "New Teacher Application",body,multipartFile.getOriginalFilename(),multipartFile);
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    @Override
    public Page<TeacherApplicationResponseDto> getAll(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<TeacherApplication> teacherApplications = teacherApplicationRepository.findAll(pageable);
        return teacherApplications.map(teacherApplicationMapper::teacherApplicationToResponseDto);
    }
}
