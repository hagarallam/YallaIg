package com.yallaIg.yallaIg_backend.service.core.report;

import com.yallaIg.yallaIg_backend.model.StudentCourse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelReportGeneratorService extends ReportGeneratorService{



    @Override
    ByteArrayOutputStream generateReport(List<StudentCourse> studentCourseList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Daily Enrollment");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Student Name");
        headerRow.createCell(1).setCellValue("Grade");
        headerRow.createCell(2).setCellValue("Course Name");
        headerRow.createCell(3).setCellValue("Amount");
        headerRow.createCell(4).setCellValue("Enrollment Date");

        // Populate data rows
        int rowNum = 1;
        for (StudentCourse studentCourse : studentCourseList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(studentCourse.getUser().getFullName());
            row.createCell(1).setCellValue(studentCourse.getStudentGrade());
            row.createCell(2).setCellValue(studentCourse.getCourse().getName());
            // todo check this , in case if multi courses will be wrong
            row.createCell(3).setCellValue(studentCourse.getCourse().getPrice());
            row.createCell(4).setCellValue(studentCourse.getCreationDate().toString());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }
}
