package com.yallaIg.yallaIg_backend.service.core.enrollment.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnrollmentResponse {

    private Integer userId ;
    private String userFullName;
    private String courseName;
    private String link ;
    private Date startDate ;
    private Date endDate;
}
