package com.csmtech.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.entity.AssessmentMaster;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.repository.AssessmentMasterRepository;
import com.csmtech.repository.SessionAssessmentMasterRepository;

@Service
public class AssesmentExcelUploadImpl implements AssesmentExcelUpload {
	
	private static final Logger logger=LoggerFactory.getLogger(AssesmentExcelUploadImpl.class);

	@Autowired
	private AssessmentMasterRepository assessmentMasterRepository;

	@Autowired
	private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;

	@Override
	public ResponseEntity<Map<String, Object>> uploadExcelSessionExcelData(MultipartFile file, Integer moduleId,
			Integer submoduleId, Integer scheduleForId, Integer sessionId) {
		logger.info("uploadExcelSessionExcelData method of AssesmentExcelUploadImpl is executed");
		Map<String, Object> response = new HashMap<>();
		try {
			// Load Excel file
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

			// Iterate through rows
			for (int i = 2; i <= sheet.getLastRowNum(); i++) { // Start from 2 to skip the first 2 rows
				Row row = sheet.getRow(i);
				if (row != null) {
					// Check if any cell in the row is empty
					boolean isEmptyRow = false;
					for (int j = 0; j < row.getLastCellNum(); j++) {
						if (row.getCell(j) == null || row.getCell(j).getCellType() == CellType.BLANK) {
							isEmptyRow = true;
							break;
						}
					}
					if (!isEmptyRow) {
						// Read data from the row
						String question = getCellValueAsString(row.getCell(0));
						String option1 = getCellValueAsString(row.getCell(1));
						String option2 = getCellValueAsString(row.getCell(2));
						String option3 = getCellValueAsString(row.getCell(3));
						String option4 = getCellValueAsString(row.getCell(4));
						String answer = getCellValueAsString(row.getCell(5));

						// Create SessionAssessmentMaster object
						SessionAssessmentMaster sessionAssessment = new SessionAssessmentMaster();

						// Instantiate nested objects and set IDs
						ModuleMaster moduleMaster = new ModuleMaster();
						moduleMaster.setModuleId(moduleId);
						sessionAssessment.setModuleMaster(moduleMaster);

						SubModule subModule = new SubModule();
						subModule.setSubmoduleId(submoduleId);
						sessionAssessment.setSubModule(subModule);

						ScheduleForMaster scheduleForMaster = new ScheduleForMaster();
						scheduleForMaster.setScheduleForId(scheduleForId);
						sessionAssessment.setScheduleForMaster(scheduleForMaster);

						SessionMaster sessionMaster = new SessionMaster();
						sessionMaster.setSessionId(sessionId);
						sessionAssessment.setSessionMaster(sessionMaster);

						// Set Excel data
						sessionAssessment.setQuestion(question);
						sessionAssessment.setOption1(option1);
						sessionAssessment.setOption2(option2);
						sessionAssessment.setOption3(option3);
						sessionAssessment.setOption4(option4);
						sessionAssessment.setAnswer(answer);
						// sessionAssessment.setCreatedBy(1);
						// sessionAssessment.setUpdatedBy(1);

						// Save to database
						sessionAssessmentMasterRepository.save(sessionAssessment);
					} else {
						// If any cell in the row is empty, skip this row
						continue;
					}
				}
			}
			workbook.close();
			response.put("success", true);
			response.put("message", "Excel data uploaded successfully");
		} catch (IOException e) {
			response.put("success", false);
			response.put("message", "Failed to upload Excel data: " + e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

	// Method to retrieve cell value as String, handling numeric and string values
	private String getCellValueAsString(Cell cell) {

		if (cell == null) {

			return null;

		}

		switch (cell.getCellType()) {

		case NUMERIC:

			// Check if the cell contains a numeric value

			if (DateUtil.isCellDateFormatted(cell)) {

				// Handle date cells if needed

				return cell.getDateCellValue().toString();

			} else {

				// Convert numeric value to string

				double numericValue = cell.getNumericCellValue();

				long intValue = (long) numericValue;

				if (numericValue == intValue) {

					// If the numeric value has no decimal part, return it as integer string

					return String.valueOf(intValue);

				} else {

					// If the numeric value has a decimal part, return it as a string

					return String.valueOf(numericValue);

				}

			}

		case STRING:

			// Retrieve string value directly

			return cell.getStringCellValue();

		case BLANK:

			// Return null for blank cells

			return null;

		default:

			// Handle other cell types if needed

			return null;

		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> uploadExcelSessionExcelData(MultipartFile file, Integer moduleId,
			Integer submoduleId, Integer scheduleForId) {
		logger.info("uploadExcelSessionExcelData method of AssesmentExcelUploadImpl is executed");
		Map<String, Object> response = new HashMap<>();
		try {
			// Load Excel file
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

			// Iterate through rows
			for (int i = 2; i <= sheet.getLastRowNum(); i++) { // Start from 2 to skip the first 2 rows
				Row row = sheet.getRow(i);
				if (row != null) {
					// Check if any cell in the row is empty
					boolean isEmptyRow = false;
					for (int j = 0; j < row.getLastCellNum(); j++) {
						if (row.getCell(j) == null || row.getCell(j).getCellType() == CellType.BLANK) {
							isEmptyRow = true;
							break;
						}
					}
					if (!isEmptyRow) {
						// Read data from the row
						String question = getCellValueAsString(row.getCell(0));
						String option1 = getCellValueAsString(row.getCell(1));
						String option2 = getCellValueAsString(row.getCell(2));
						String option3 = getCellValueAsString(row.getCell(3));
						String option4 = getCellValueAsString(row.getCell(4));
						String answer = getCellValueAsString(row.getCell(5));

						// Create AssessmentMaster object
						AssessmentMaster assessmentMaster = new AssessmentMaster();
						assessmentMaster.setModuleId(moduleId);
						assessmentMaster.setSubmoduleId(submoduleId);
						assessmentMaster.setScheduleForId(scheduleForId);
						assessmentMaster.setQuestion(question);
						assessmentMaster.setOption1(option1);
						assessmentMaster.setOption2(option2);
						assessmentMaster.setOption3(option3);
						assessmentMaster.setOption4(option4);
						assessmentMaster.setAnswer(answer);
						assessmentMaster.setCreatedBy(1);
						assessmentMaster.setUpdatedBy(1);

						// Save to database
						assessmentMasterRepository.save(assessmentMaster);
					} else {
						// If any cell in the row is empty, skip this row
						continue;
					}
				}
			}
			workbook.close();
			response.put("success", true);
			response.put("message", "Excel data uploaded successfully");
		} catch (IOException e) {
			response.put("success", false);
			response.put("message", "Failed to upload Excel data: " + e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

}
