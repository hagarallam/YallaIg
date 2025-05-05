package com.yallaIg.yallaIg_backend.service.core.report;

import com.yallaIg.yallaIg_backend.model.StudentCourse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public abstract class ReportGeneratorService {

    abstract ByteArrayOutputStream generateReport(List<StudentCourse> studentCourseList) throws IOException;

}
