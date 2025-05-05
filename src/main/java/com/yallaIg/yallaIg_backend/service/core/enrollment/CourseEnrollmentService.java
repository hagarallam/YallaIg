package com.yallaIg.yallaIg_backend.service.core.enrollment;

import com.yallaIg.yallaIg_backend.dto.request.ProductRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.service.core.enrollment.models.EnrollmentResponse;

import java.util.List;

public interface CourseEnrollmentService {

    EnrollmentResponse enroll(ProductRegistrationRequestDto productRegistrationRequestDto, int orderId);
}
