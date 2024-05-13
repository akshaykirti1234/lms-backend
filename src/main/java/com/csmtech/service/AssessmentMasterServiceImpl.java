package com.csmtech.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.AssessmentMasterDto;
import com.csmtech.entity.AssessmentMaster;
import com.csmtech.repository.AssessmentMasterRepository;
import com.csmtech.repository.ScheduleForMasterRepository;
import com.csmtech.repository.SessionAssessmentMasterRepository;

@Service
public class AssessmentMasterServiceImpl implements AssessmentMasterService {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentMasterServiceImpl.class);

	@Autowired
	private AssessmentMasterRepository assessmentMasterRepository;

	@Autowired
	ScheduleForMasterRepository scheduleForMasterRepository;

	@Autowired
	SessionAssessmentMasterRepository sessionAssessmentMasterRepository;

	@Override
	public AssessmentMaster saveAssessment(AssessmentMasterDto assessmentDto) {
		AssessmentMaster assMaster= new AssessmentMaster();
		
		assMaster.setAssessmentId(assessmentDto.getAssessmentId());
		assMaster.setModuleId(assessmentDto.getModuleId());
		assMaster.setSubmoduleId(assessmentDto.getSubmoduleId());
		assMaster.setScheduleForId(assessmentDto.getScheduleForId());
		assMaster.setQuestion(assessmentDto.getQuestion());
		assMaster.setOption1(assessmentDto.getOption1());
		assMaster.setOption2(assessmentDto.getOption2());
		assMaster.setOption3(assessmentDto.getOption3());
		assMaster.setOption4(assessmentDto.getOption4());
		assMaster.setAnswer(assessmentDto.getAnswer());
		assMaster.setCreatedBy(1);
		assMaster.setUpdatedBy(1);
		return assessmentMasterRepository.save(assMaster);
	}
	

	@Override
	public List<Map<String, Object>> viewAssessmentData() {
		logger.info("viewAssessmentData method of AssessmentMasterServiceImpl is executed");
		return assessmentMasterRepository.getAllAssesmentdata();

	}

	@Override
	public void deleteAssessment(Integer id) {
		logger.info("deleteAssessment method of AssessmentMasterServiceImpl is executed");
		assessmentMasterRepository.deleteAssessment(id);

	}

	@Override
	public Map<String, Object> getAssessmentById(Integer id) {
		logger.info("getAssessmentById method of AssessmentMasterServiceImpl is executed");
		return assessmentMasterRepository.updateAssessment(id);
	}

	
	
	// For upload excel
	
	
	@Override
	public List<Map<String, Object>> retriveModuleTypeList() {
		return assessmentMasterRepository.retriveModuleTypeList();
	}

	@Override
	public List<Map<String, Object>> retriveSubModuleList() {
		return assessmentMasterRepository.retriveSubModuleList();
	}

	@Override
	public List<Map<String, Object>> retriveScheduleForList() {

		return assessmentMasterRepository.retriveScheduleForList();
	}



	// For Module based Questions uplaod
	@Override
	public ResponseEntity<Map<String, Object>> uploadExcelData(MultipartFile file) {
		Map<String, Object> responseMap = new HashMap<>();
		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

			// Retrieve module, submodule, and schedulefor data from database
			List<Map<String, Object>> moduleList = retriveModuleTypeList();
			List<Map<String, Object>> subModuleList = retriveSubModuleList();
			List<Map<String, Object>> scheduleForList = retriveScheduleForList();

			// Iterate through rows to extract data and save to the database
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Assuming the first row is header
				Row row = sheet.getRow(i);

				// Check if the first cell of the row is null or empty, indicating an empty row
				Cell firstCell = row.getCell(0);
				if (firstCell == null || firstCell.getCellType() == CellType.BLANK) {
					break; // Exit the loop if an empty row is encountered
				}

				AssessmentMaster assessmentMaster = new AssessmentMaster();

				// Retrieve moduleId, submoduleId, and scheduleForId from Excel
				String moduleType = getStringCellValue(row.getCell(0));
				String submoduleName = getStringCellValue(row.getCell(1));
				String scheduleFor = getStringCellValue(row.getCell(2));

				// Find the corresponding IDs from the retrieved lists
				int moduleId = findIdFromList(moduleType, moduleList, "MODULENAME", "MODULEID");
				int submoduleId = findIdFromList(submoduleName, subModuleList, "SUBMODULENAME", "SUBMODULEID");
				int scheduleForId = findIdFromList(scheduleFor, scheduleForList, "SCHEDULEFOR", "SCHEDULEFORID");

				assessmentMaster.setModuleId(moduleId);
				assessmentMaster.setSubmoduleId(submoduleId);
				assessmentMaster.setScheduleForId(scheduleForId);

				// Set other fields from Excel
				assessmentMaster.setQuestion(getStringCellValue(row.getCell(3)));
				assessmentMaster.setOption1(getStringCellValue(row.getCell(4)));
				assessmentMaster.setOption2(getStringCellValue(row.getCell(5)));
				assessmentMaster.setOption3(getStringCellValue(row.getCell(6)));
				assessmentMaster.setOption4(getStringCellValue(row.getCell(7)));
				assessmentMaster.setAnswer(getStringCellValue(row.getCell(8)));
				assessmentMaster.setCreatedBy(1);
				assessmentMaster.setUpdatedBy(1);

				assessmentMasterRepository.save(assessmentMaster);
			}
			responseMap.put("success", true);
			responseMap.put("message", "Data uploaded successfully.");
			return ResponseEntity.ok(responseMap);
		} catch (IOException e) {
			e.printStackTrace();
			responseMap.put("success", false);
			responseMap.put("message", "Failed to upload data. Please try again.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
		}
	}


	// Method to find ID from list based on name
	private int findIdFromList(String name, List<Map<String, Object>> list, String nameKey, String idKey) {
		for (Map<String, Object> item : list) {
			if (name.equals(item.get(nameKey))) {
				return (int) item.get(idKey);
			}
		}
		return -1; // Return -1 if not found
	}

	private String getStringCellValue(Cell cell) {
		if (cell != null) {
			switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case FORMULA:
				return cell.getCellFormula(); // You may need to handle formula cells differently
			default:
				return ""; // Return empty string for other cell types
			}
		}
		return ""; // Return empty string if cell is null
	}
}
