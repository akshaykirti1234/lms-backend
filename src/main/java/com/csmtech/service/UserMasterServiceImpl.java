package com.csmtech.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.commons.collections4.map.HashedMap;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.UserMasterDTO;
import com.csmtech.entity.Location;
import com.csmtech.entity.UserMaster;
import com.csmtech.entity.UserType;
import com.csmtech.repository.UserMasterRepository;
import com.csmtech.util.PasswordGenerator;

@Service
public class UserMasterServiceImpl implements UserMasterService {

	@Autowired
	private UserMasterRepository userMasterRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserMasterServiceImpl.class);

	@Override
	public ResponseEntity<?> loginValidate(UserMasterDTO userMasterDTO) {
		String emailId = userMasterDTO.getEmailId();
		String password = userMasterDTO.getPassword();

		UserMaster user = userMasterRepository.getUserByEmail(emailId);

		if (user.getUserId() != null) {
			if (user.getPassword().equals(password)) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public ResponseEntity<?> getAllUsers() {
		try {
			List<Object> userList = userMasterRepository.getAllUsers();
			return new ResponseEntity<>(userList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	============================================================================

	@Override
	public UserMaster saveUserMaster(UserMasterDTO userMasterDto) {

		Location location = new Location();
		location.setLocationId(userMasterDto.getLocation());

		UserType userType = new UserType();
		userType.setUserTypeId(2);

		UserMaster userMaster = new UserMaster();
		userMaster.setFullName(userMasterDto.getFullName());
		userMaster.setContactNo(userMasterDto.getContactNo());
		userMaster.setEmailId(userMasterDto.getEmailId());
		userMaster.setDepartment(userMasterDto.getDepartment());
		userMaster.setDesignation(userMasterDto.getDesignation());
		userMaster.setLocation(location);
		userMaster.setUserType(userType);

		userMaster.setCreatedBy(1);

		// userMaster.setDeletedFlag(false);
		if (userMasterDto.getUserId() == 0) {

			String generatedPassword = PasswordGenerator.generateRandomPassword(8);
			userMaster.setPassword(new BCryptPasswordEncoder().encode(generatedPassword));
			userMaster.setNormalPassword(generatedPassword);

			// Send password via email
			// log.info("Password created");
			// EmailSender emailSender = new EmailSender();
			// emailSender.sendPasswordByEmail(userMasterDto.getEmailId(),
			// generatedPassword);
		} else {
			userMaster.setUserId(userMasterDto.getUserId());
			userMaster.setPassword(userMasterDto.getPassword());
			userMaster.setNormalPassword(userMasterDto.getNormalPassword());
			userMaster.setUpdatedBy(1);

		}
		return userMasterRepository.save(userMaster);

	}

	@Override
	public List<Map<String, Object>> getUseMasterList() {
		return userMasterRepository.getUseMasterList();
	}

	@Override
	public void deleteUserMaster(Integer userId) {
		userMasterRepository.deleteUserMaster(userId);
	}

	@Override
	public Map<String, Object> getUserMasterById(Integer userId) {
		return userMasterRepository.getUserMasterById(userId);
	}

	@Override
	public List<Map<String, Object>> gettEmailList() {
		return userMasterRepository.getEmailList();
	}

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	// Method to check if the email format is valid

	private boolean isValidEmail(String email, List<String> invalidEmailNames) {

		Matcher matcher = EMAIL_PATTERN.matcher(email);

		if (!matcher.matches()) {

			invalidEmailNames.add(email);

			return false;

		}

		return true;

	}

	@Override

	@Transactional

	public ResponseEntity<Map<String, Object>> uploadUserData(MultipartFile file) {

		Map<String, Object> response = new HashedMap<>();

		List<String> existingEmails = new ArrayList<>(); // List to store validations list

		List<String> existingEmailsNames = new ArrayList<>();

		List<String> invalidContacts = new ArrayList<>();

		List<String> invalidDesignations = new ArrayList<>();

		List<String> invalidDepartments = new ArrayList<>();

		List<String> invalidEmails = new ArrayList<>();

		try (InputStream inputStream = file.getInputStream();

				Workbook workbook = new XSSFWorkbook(inputStream)) { // Using XSSFWorkbook for.xlsx files

			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> iterator = sheet.iterator();

			int rowCount = 0;

			// Skip the first two rows (assuming the header and user list heading)

			while (iterator.hasNext() && rowCount < 2) {

				iterator.next();

				rowCount++;

			}

			// Initialize password encoder

			// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new
			// SecureRandom());

			// Process each row and perform validation checks

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();

				String name = currentRow.getCell(0).getStringCellValue();

				String password = generateRandomPassword();

				String contactNo = currentRow.getCell(1).getCellType() == CellType.NUMERIC

						? String.valueOf((long) currentRow.getCell(1).getNumericCellValue())

						: currentRow.getCell(1).getStringCellValue();

				// Check contact number length

				if (contactNo.length() != 10) {

					invalidContacts.add(name);

				}

				String emailId = currentRow.getCell(2).getStringCellValue();

				String designation = currentRow.getCell(3).getStringCellValue();

				String department = currentRow.getCell(4).getStringCellValue();

				String location = currentRow.getCell(5).getCellType() == CellType.NUMERIC

						? String.valueOf((int) currentRow.getCell(5).getNumericCellValue())

						: currentRow.getCell(5).getStringCellValue();

				// Validate email format

				if (!isValidEmail(emailId, invalidEmails)) {

					invalidEmails.add(name);

				}

				// Validate designation

				if (!isValidAlphanumericWithSpace(designation)) {

					invalidDesignations.add(name);

				}

				// Validate department

				if (!isValidAlphanumericWithSpace(department)) {

					invalidDepartments.add(name);

				}

				// Check if email already exists

				if (userMasterRepository.existsByEmailId(emailId)) {

					// Add duplicate email to the list

					existingEmails.add(emailId);

					existingEmailsNames.add(name);

				}

			}

			// Check if any validation failed for any row

			if (!existingEmails.isEmpty() || !existingEmailsNames.isEmpty() || !invalidContacts.isEmpty()

					|| !invalidDesignations.isEmpty() || !invalidDepartments.isEmpty() || !invalidEmails.isEmpty()) {

				// Construct error message based on failed validations

				StringBuilder errorMessage = new StringBuilder();

				if (!existingEmails.isEmpty()) {

					errorMessage.append("These email IDs already exist: ").append(existingEmails)

							.append(". Please upload valid Email for these names: ").append(existingEmailsNames)

							.append("\n");

				}

				if (!invalidContacts.isEmpty()) {

					errorMessage.append("Please ensure valid 10-digit contact numbers for the names. ")

							.append(invalidContacts).append("\n");

				}

				if (!invalidEmails.isEmpty()) {

					errorMessage.append("Please enter valid Email format for: ").append(invalidEmails).append("\n");

				}

				if (!invalidDesignations.isEmpty()) {

					errorMessage.append("These designations have invalid formats for the associated names: ")

							.append(invalidDesignations).append("\n");

				}

				if (!invalidDepartments.isEmpty()) {

					errorMessage.append("These departments have invalid formats for the associated names: ")

							.append(invalidDepartments).append("\n");

				}

				response.put("errorMsg", errorMessage.toString());

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

			} else {

				// If everything went well, proceed to save the data to the database

				// Reset the iterator to process rows again

				iterator = sheet.iterator();

				rowCount = 0;

				// Skip the first two rows (assuming the header and user list heading)

				while (iterator.hasNext() && rowCount < 2) {

					iterator.next();

					rowCount++;

				}

				// Process each row and save data to the database

				while (iterator.hasNext()) {

					Row currentRow = iterator.next();

					String name = currentRow.getCell(0).getStringCellValue();

					String password = generateRandomPassword();

					String contactNo = currentRow.getCell(1).getCellType() == CellType.NUMERIC

							? String.valueOf((long) currentRow.getCell(1).getNumericCellValue())

							: currentRow.getCell(1).getStringCellValue();

					String emailId = currentRow.getCell(2).getStringCellValue();

					String designation = currentRow.getCell(3).getStringCellValue();

					String department = currentRow.getCell(4).getStringCellValue();

					String location = currentRow.getCell(5).getCellType() == CellType.NUMERIC

							? String.valueOf((int) currentRow.getCell(5).getNumericCellValue())

							: currentRow.getCell(5).getStringCellValue();

					// Save data to the database

					UserMaster user = new UserMaster();

					user.setPassword(new BCryptPasswordEncoder().encode(password));

					user.setNormalPassword(password);

					user.setFullName(name);

					user.setContactNo(contactNo);

					user.setEmailId(emailId);

					user.setDesignation(designation);

					user.setDepartment(department);

					UserType ut = new UserType();
					ut.setUserTypeId(2);
					user.setUserType(ut);
					Location l = new Location();
					l.setLocationId(1);
					user.setLocation(l);

					user.setCreatedBy(1);

					user.setUpdatedBy(1);

					user.setCreatedOn(new Date());

					user.setUpdatedOn(new Date());

					user.setDeletedFlag(false);

					userMasterRepository.save(user);

				}

				// Return success response
				response.put("message", "User data uploaded successfully.");
				return ResponseEntity.ok(response);

			}

		} catch (IOException e) {

			logger.error("An IO exception occurred while uploading user data: {}", e.getMessage());
			response.put("errorMsg", "Failed to upload user data. Please try again later.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

					.body(response);

		} catch (Exception e) {

			logger.error("An unexpected exception occurred: {}", e.getMessage());
			response.put("errorMsg", "An unexpected error occurred. Please contact the administrator.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

					.body(response);

		}

	}

	private boolean isValidAlphanumericWithSpace(String str) {

		return str.matches("^[a-zA-Z0-9 ]+$");

	}

	private String generateRandomPassword() {

		// Define characters to be used in the password

		String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&";

		// Initialize secure random object

		SecureRandom random = new SecureRandom();

		// Generate random password of length 10

		StringBuilder password = new StringBuilder();

		for (int i = 0; i < 10; i++) {

			int randomIndex = random.nextInt(allChars.length());

			password.append(allChars.charAt(randomIndex));

		}

		return password.toString();

	}

}
