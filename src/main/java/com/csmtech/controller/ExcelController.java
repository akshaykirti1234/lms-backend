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

import com.csmtech.service.UserMasterService;

@RestController
@CrossOrigin("*")
public class ExcelController {

	@Autowired
	private UserMasterService userService;

	@GetMapping("/generate-excel")
	public ResponseEntity<byte[]> generateExcel() throws IOException {
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
			headingCell.setCellValue("User Lists");
			headingCell.setCellStyle(boldStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

			Row headerRow = sheet.createRow(1);
			String[] headers = { "Name", "Contact no", "Email ID", "Designation", "Department", "Location" };
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
			headers1.setContentDispositionFormData("attachment", "UserList.xlsx");

			return new ResponseEntity<>(outputStream.toByteArray(), headers1, HttpStatus.OK);
		}
	}

	@PostMapping("/upload")

	public ResponseEntity<?> uploadExcelData(@RequestParam("file") MultipartFile file) {

		try {

			ResponseEntity<Map<String, Object>> response = userService.uploadUserData(file);

			if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {

// If duplicate email IDs found, return the message provided by the service

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getBody());

			} else {

				return ResponseEntity.status(HttpStatus.OK).body(response.getBody());

			}

		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

					.body("Failed to upload Excel data: " + e.getMessage());

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

					.body("An unexpected error occurred: " + e.getMessage());

		}

	}

}
