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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.SessionAssessmentMasterDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.repository.SessionAssessmentMasterRepository;
import com.csmtech.repository.SessionAssessmentSettingRepository;

@Service
public class SessionAssessmentMasterServiceImpl implements SessionAssessmentMasterService {

	@Autowired
	private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;
	@Autowired
	private SessionAssessmentSettingRepository assessmentSettingRepository;

	@Override
	public ResponseEntity<?> getQuestionarBySessionId(Integer sessionId) {
		SessionAssessmentSetting sessionSessionAssessmentSetting = assessmentSettingRepository
				.findFirst1BySessionMaster_SessionId(sessionId);

		Integer noOfQuestion = sessionSessionAssessmentSetting.getNumberOfQuestion();
		System.err.println(sessionId + " " + noOfQuestion);
		List<SessionAssessmentMaster> assessmentMastersList = sessionAssessmentMasterRepository
				.getQuestionarBySessionId(sessionId, noOfQuestion);

		if (assessmentMastersList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for session ID: " + sessionId);
		} else {
			return ResponseEntity.ok(assessmentMastersList);
		}
	}

	@Override
	public SessionAssessmentMaster saveAssessmentSession(SessionAssessmentMasterDto assessmentSessionDto) {
		SessionAssessmentMaster sessionAs = new SessionAssessmentMaster();

		sessionAs.setSessionAssessmentMasterId(assessmentSessionDto.getAssessmentId());

		ModuleMaster ms = new ModuleMaster();
		ms.setModuleId(assessmentSessionDto.getModuleId());
		sessionAs.setModuleMaster(ms);

		SubModule subMo = new SubModule();
		subMo.setSubmoduleId(assessmentSessionDto.getSubmoduleId());
		sessionAs.setSubModule(subMo);

		ScheduleForMaster schMas = new ScheduleForMaster();
		schMas.setScheduleForId(assessmentSessionDto.getScheduleForId());
		sessionAs.setScheduleForMaster(schMas);

		SessionMaster sessiMas = new SessionMaster();
		sessiMas.setSessionId(assessmentSessionDto.getSessionId());
		sessionAs.setSessionMaster(sessiMas);

		sessionAs.setQuestion(assessmentSessionDto.getQuestion());
		sessionAs.setOption1(assessmentSessionDto.getOption1());
		sessionAs.setOption2(assessmentSessionDto.getOption2());
		sessionAs.setOption3(assessmentSessionDto.getOption3());
		sessionAs.setOption4(assessmentSessionDto.getOption4());
		sessionAs.setAnswer(assessmentSessionDto.getAnswer());
		return sessionAssessmentMasterRepository.save(sessionAs);
	}

	@Override
	public List<Map<String, Object>> viewAssessmentForSessionData() {
		return sessionAssessmentMasterRepository.viewAssessmentForSessionData();
	}

	@Override
	public void deleteAssessmentSession(Integer id) {
		sessionAssessmentMasterRepository.deleteAssessmentSession(id);
	}

	@Override
	public Map<String, Object> getAssessmentSessionById(Integer id) {
		return sessionAssessmentMasterRepository.getAssessmentSessionById(id);
	}

	// For upload excel

	@Override
	public List<Map<String, Object>> retriveModuleTypeList() {
		return sessionAssessmentMasterRepository.retriveModuleTypeList();
	}

	@Override
	public List<Map<String, Object>> retriveSubModuleList() {
		return sessionAssessmentMasterRepository.retriveSubModuleList();
	}

	@Override
	public List<Map<String, Object>> retriveScheduleForList() {

		return sessionAssessmentMasterRepository.retriveScheduleForList();
	}

	@Override
	public List<Map<String, Object>> retriveSessionList() {

		return sessionAssessmentMasterRepository.retriveSessionList();
	}

	@Override
	public ResponseEntity<Map<String, Object>> uploadSessionExcelData(MultipartFile file) {
		Map<String, Object> responseMap = new HashMap<>();
		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

			// Retrieve module, submodule, and schedulefor data from database
			List<Map<String, Object>> moduleList = retriveModuleTypeList();
			List<Map<String, Object>> subModuleList = retriveSubModuleList();
			List<Map<String, Object>> scheduleForList = retriveScheduleForList();
			List<Map<String, Object>> sessionList = retriveSessionList();

			// Iterate through rows to extract data and save to the database
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Assuming the first row is header
				Row row = sheet.getRow(i);

				// Check if the first cell of the row is null or empty, indicating an empty row
				Cell firstCell = row.getCell(0);
				if (firstCell == null || firstCell.getCellType() == CellType.BLANK) {
					break; // Exit the loop if an empty row is encountered
				}

				SessionAssessmentMaster assessmentSessionMaster = new SessionAssessmentMaster();

				// Retrieve moduleId, submoduleId, and scheduleForId from Excel
				String moduleType = getStringCellValue(row.getCell(0));
				String submoduleName = getStringCellValue(row.getCell(1));
				String scheduleFor = getStringCellValue(row.getCell(2));
				String sessionName = getStringCellValue(row.getCell(3));

				// To Find the corresponding IDs from the retrieved lists
				int moduleId = findIdFromList(moduleType, moduleList, "MODULENAME", "MODULEID");
				int submoduleId = findIdFromList(submoduleName, subModuleList, "SUBMODULENAME", "SUBMODULEID");
				int scheduleForId = findIdFromList(scheduleFor, scheduleForList, "SCHEDULEFOR", "SCHEDULEFORID");
				int sessionId = findIdFromList(sessionName, sessionList, "SESSIONNAME", "SESSIONID");

				// nested objects are instantiated if null
				if (assessmentSessionMaster.getModuleMaster() == null) {
					assessmentSessionMaster.setModuleMaster(new ModuleMaster());
				}
				if (assessmentSessionMaster.getSubModule() == null) {
					assessmentSessionMaster.setSubModule(new SubModule());
				}
				if (assessmentSessionMaster.getScheduleForMaster() == null) {
					assessmentSessionMaster.setScheduleForMaster(new ScheduleForMaster());
				}
				if (assessmentSessionMaster.getSessionMaster() == null) {
					assessmentSessionMaster.setSessionMaster(new SessionMaster());
				}

				// Set IDs into assessmentSessionMaster
				ModuleMaster moduleMaster = assessmentSessionMaster.getModuleMaster();
				if (moduleMaster != null) {
					moduleMaster.setModuleId(moduleId);
				}

				SubModule subModule = assessmentSessionMaster.getSubModule();
				if (subModule != null) {
					subModule.setSubmoduleId(submoduleId);
				}

				ScheduleForMaster scheduleForMaster = assessmentSessionMaster.getScheduleForMaster();
				if (scheduleForMaster != null) {
					scheduleForMaster.setScheduleForId(scheduleForId);
				}

				SessionMaster sessionMaster = assessmentSessionMaster.getSessionMaster();
				if (sessionMaster != null) {
					sessionMaster.setSessionId(sessionId);
				}

				// Set other fields from Excel
				assessmentSessionMaster.setQuestion(getStringCellValue(row.getCell(3)));
				assessmentSessionMaster.setOption1(getStringCellValue(row.getCell(4)));
				assessmentSessionMaster.setOption2(getStringCellValue(row.getCell(5)));
				assessmentSessionMaster.setOption3(getStringCellValue(row.getCell(6)));
				assessmentSessionMaster.setOption4(getStringCellValue(row.getCell(7)));
				assessmentSessionMaster.setAnswer(getStringCellValue(row.getCell(8)));
				// assessmentSessionMaster.setCreatedBy(1);
				// assessmentSessionMaster.setUpdatedBy(1);

				sessionAssessmentMasterRepository.save(assessmentSessionMaster);
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
