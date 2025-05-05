package com.yallaIg.yallaIg_backend.service.core.report;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface ReportService {

    default void sendDailyEnrollmentReport() throws MessagingException, IOException {

    }
}
