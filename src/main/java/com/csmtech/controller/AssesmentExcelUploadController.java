package com.csmtech.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.service.AssesmentExcelUpload;

@RestController
@CrossOrigin("*")
public class AssesmentExcelUploadController {

	@Autowired
	private AssesmentExcelUpload assesmentExcelUpload;

	@GetMapping("/generateSAssesmentExcel")
	public ResponseEntity<byte[]> generateAssesmentExcel() throws IOException {
		try (Workbook workbook = new XSSFWorkbook()) {

			Sheet sheet = workbook.createSheet("Template");

			// Create font style for bold
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);

			// Create cell style with bold font
			CellStyle boldStyle = workbook.createCellStyle();
			boldStyle.setFont(boldFont);
			boldStyle.setAlignment(HorizontalAlignment.CENTER);

			Row headingRow = sheet.createRow(0);
			Cell headingCell = headingRow.createCell(0);
			headingCell.setCellValue("Assesment Data");
			headingCell.setCellStyle(boldStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

			Row headerRow = sheet.createRow(1);
			String[] headers = { "Question", "Option-1", "Option-2", "Option-3", "Option-4", "Answer" };
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(boldStyle);
			}

			// Auto-size columns for better readability
			for (int i = 0; i < headers.length; i++) {
				sheet.autoSizeColumn(i);
			}

			// Write workbook to ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);

			// Set response headers for Excel file download
			HttpHeaders headers1 = new HttpHeaders();
			headers1.setContentType(
					MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
			headers1.setContentDispositionFormData("attachment", "QuestionList.xlsx");

			return new ResponseEntity<>(outputStream.toByteArray(), headers1, HttpStatus.OK);
		}
	}

	@PostMapping("/uploadExcelSessionExcel")
	public ResponseEntity<?> uploadExcelSession(@RequestParam("file") MultipartFile file,
			@RequestParam("moduleId") Integer moduleId, @RequestParam("subModuleId") Integer submoduleId,
			@RequestParam("scheduleId") Integer scheduleForId,
			@RequestParam(name = "sessionId", required = false) Integer sessionId) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Please select a file to upload.");
		}

		// Pass the additional parameters to the service method
		ResponseEntity<Map<String, Object>> response = null;
		if (sessionId != null) {
			response = assesmentExcelUpload.uploadExcelSessionExcelData(file, moduleId, submoduleId, scheduleForId,
					sessionId);
		} else {
			response = assesmentExcelUpload.uploadExcelSessionExcelData(file, moduleId, submoduleId, scheduleForId);
		}

		if (response != null && response.getBody() != null) {
			return ResponseEntity.ok().body(response.getBody());
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload Excel data.");
		}

	}
}


