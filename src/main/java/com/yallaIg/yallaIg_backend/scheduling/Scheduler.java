package com.yallaIg.yallaIg_backend.scheduling;

import com.yallaIg.yallaIg_backend.service.core.report.EnrollmentReportService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Scheduler {

    private final EnrollmentReportService enrollmentReportService;

    @Scheduled(cron = "0 */30 * ? * *") // 2 min for testing
    public void sendDailyReport() throws MessagingException, IOException {
        enrollmentReportService.sendDailyEnrollmentReport();
    }
}
