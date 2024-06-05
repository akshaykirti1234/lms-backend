package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.service.UserMasterService;

@ExtendWith(MockitoExtension.class)
public class ExcelControllerTest {

    @Mock
    private UserMasterService userService;

    @InjectMocks
    private ExcelController excelController;

    @Test
    public void testGenerateExcel() throws IOException {
        // Test the generateExcel method
        ResponseEntity<byte[]> response = excelController.generateExcel();

        // Assert that the response is not null and has the expected status code
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // You can further test the content of the Excel file if needed
        // For example, you can check if the response body contains a valid Excel file
        // Here, we're just checking that the response body is not null
        assertNotNull(response.getBody());
    }

    @Test
    public void testUploadExcelData() throws IOException {
        // Mock a MultipartFile
        MultipartFile file = mock(MultipartFile.class);

        // Mock the response from userService.uploadUserData
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Upload successful");
        ResponseEntity<Map<String, Object>> userServiceResponse = ResponseEntity.ok(responseBody);
        when(userService.uploadUserData(file)).thenReturn(userServiceResponse);

        // Test the uploadExcelData method
        ResponseEntity<?> response = excelController.uploadExcelData(file);

        // Assert that the response is not null and has the expected status code
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body is as expected
        assertEquals(responseBody, response.getBody());
    }
}
