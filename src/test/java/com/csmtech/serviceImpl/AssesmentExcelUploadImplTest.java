package com.csmtech.serviceImpl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.repository.AssessmentMasterRepository;
import com.csmtech.repository.SessionAssessmentMasterRepository;
import com.csmtech.service.AssesmentExcelUploadImpl;

@ExtendWith(MockitoExtension.class)
public class AssesmentExcelUploadImplTest {

    @InjectMocks
    private AssesmentExcelUploadImpl assesmentExcelUpload;

    @Mock
    private AssessmentMasterRepository assessmentMasterRepository;

    @Mock
    private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;

    @Mock
    private Logger logger;

    private MultipartFile mockFile;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Creating a mock Excel file
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Adding header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Question");
        header.createCell(1).setCellValue("Option1");
        header.createCell(2).setCellValue("Option2");
        header.createCell(3).setCellValue("Option3");
        header.createCell(4).setCellValue("Option4");
        header.createCell(5).setCellValue("Answer");

        // Adding data rows
        for (int i = 1; i <= 5; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue("Question " + i);
            row.createCell(1).setCellValue("Option1 " + i);
            row.createCell(2).setCellValue("Option2 " + i);
            row.createCell(3).setCellValue("Option3 " + i);
            row.createCell(4).setCellValue("Option4 " + i);
            row.createCell(5).setCellValue("Answer " + i);
        }

        // Convert workbook to byte array
        byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            byteArray = bos.toByteArray();
        }
        workbook.close();

        // Create mock multipart file
        mockFile = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", byteArray);
    }

    @Test
    public void testUploadExcelSessionExcelData_Success() throws IOException {
        Integer moduleId = 1;
        Integer submoduleId = 1;
        Integer scheduleForId = 1;
        Integer sessionId = 1;

        ResponseEntity<Map<String, Object>> response = assesmentExcelUpload.uploadExcelSessionExcelData(mockFile, moduleId, submoduleId, scheduleForId, sessionId);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("success"));
        assertEquals("Excel data uploaded successfully", response.getBody().get("message"));
        verify(sessionAssessmentMasterRepository, times(5)).save(any(SessionAssessmentMaster.class));
    }

    @Test
    public void testUploadExcelSessionExcelData_Failure() throws IOException {
        doThrow(new RuntimeException("Database Error")).when(sessionAssessmentMasterRepository).save(any(SessionAssessmentMaster.class));

        Integer moduleId = 1;
        Integer submoduleId = 1;
        Integer scheduleForId = 1;
        Integer sessionId = 1;

        ResponseEntity<Map<String, Object>> response = assesmentExcelUpload.uploadExcelSessionExcelData(mockFile, moduleId, submoduleId, scheduleForId, sessionId);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("success"));
        assertTrue(((String) response.getBody().get("message")).contains("Failed to upload Excel data"));
    }

    @Test
    public void testUploadExcelSessionExcelData_EmptyRow() throws IOException {
        // Creating a mock Excel file with an empty row
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Adding header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Question");
        header.createCell(1).setCellValue("Option1");
        header.createCell(2).setCellValue("Option2");
        header.createCell(3).setCellValue("Option3");
        header.createCell(4).setCellValue("Option4");
        header.createCell(5).setCellValue("Answer");

        // Adding data rows
        for (int i = 1; i <= 3; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue("Question " + i);
            row.createCell(1).setCellValue("Option1 " + i);
            row.createCell(2).setCellValue("Option2 " + i);
            row.createCell(3).setCellValue("Option3 " + i);
            row.createCell(4).setCellValue("Option4 " + i);
            row.createCell(5).setCellValue("Answer " + i);
        }

        // Adding an empty row
        sheet.createRow(4);

        // Convert workbook to byte array
        byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            byteArray = bos.toByteArray();
        }
        workbook.close();

        // Create mock multipart file
        mockFile = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", byteArray);

        Integer moduleId = 1;
        Integer submoduleId = 1;
        Integer scheduleForId = 1;
        Integer sessionId = 1;

        ResponseEntity<Map<String, Object>> response = assesmentExcelUpload.uploadExcelSessionExcelData(mockFile, moduleId, submoduleId, scheduleForId, sessionId);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("success"));
        assertEquals("Excel data uploaded successfully", response.getBody().get("message"));
        verify(sessionAssessmentMasterRepository, times(3)).save(any(SessionAssessmentMaster.class)); // Only 3 rows should be saved
    }
}
