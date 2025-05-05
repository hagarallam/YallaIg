package com.yallaIg.yallaIg_backend.service.core.report;

import com.yallaIg.yallaIg_backend.model.StudentCourse;
import com.yallaIg.yallaIg_backend.service.core.crud.StudentCourseService;
import com.yallaIg.yallaIg_backend.service.mail.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentReportService implements ReportService{

    @Value("${admin.mail}")
    private String adminMail;

    private final StudentCourseService studentCourseService;
    private final ReportGeneratorService reportGeneratorService;
    private final EmailService emailService;

    @Override
    public void sendDailyEnrollmentReport() throws MessagingException, IOException {
        // get all enrollment in a day
        List<StudentCourse> studentCourseList = studentCourseService.getTodayEnrollment();

        // get Excel file
        ByteArrayOutputStream report  = reportGeneratorService.generateReport(studentCourseList);

        //send mail
        emailService.sendMailWithAttachment(adminMail,
                "Daily Enrollment Sheet",
                getBody(),
                "DailyEnrollment.xlsx",
                new ByteArrayResource(report.toByteArray()));
    }

    private String getBody() {
      return  "Hello Admins ! \n " +
              "Please find attached the daily Enrollment report.\n" +
              "Thanks !";
    }

}
