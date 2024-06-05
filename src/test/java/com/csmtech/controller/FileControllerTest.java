package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.service.FileService;

public class FileControllerTest {

	@Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @Mock
    private MultipartFile file;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetTempFile() throws IOException {
        // Mock data
        String name = "IT SESSION";

        // Mock service response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "File uploaded successfully");

        // Mock service method
        when(fileService.setTempFileService(any(MultipartFile.class), any(String.class))).thenReturn(ResponseEntity.ok(response));

        // Call the controller method
        ResponseEntity<Map<String, Object>> result = fileController.setTempFile(file, name);

        // Verify the result
        assertEquals(response, result.getBody());
        assertEquals(200, result.getStatusCodeValue());

        // Verify service method is called
        verify(fileService).setTempFileService(file, name);
    }

	@SuppressWarnings("rawtypes")
	@Test
	public void testDownloadDocument() throws Exception {
		// Mock data
		HttpServletResponse response = null; // Your mock response
		String fileName = "example.txt";

		// Mock service response
		byte[] fileContent = null; // Your mock file content
		ResponseEntity<?> expectedResponseEntity = ResponseEntity.ok(fileContent);

		// Mock service method
		when(fileService.downloadDocument(fileName)).thenReturn((ResponseEntity) expectedResponseEntity);

		// Call the controller method
		ResponseEntity<?> result = fileController.downloadDocument(response, fileName);

		// Verify the result
		assertEquals(expectedResponseEntity, result);
		assertEquals(expectedResponseEntity.getStatusCodeValue(), result.getStatusCodeValue());

		// Verify service method is called
		verify(fileService).downloadDocument(fileName);
	}
}
