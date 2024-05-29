package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.UserMasterDTO;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.UserMasterRepository;
import com.csmtech.service.UserMasterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class UserMasterServiceImplTest {
	
	@Mock
    private UserMasterRepository userMasterRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserMasterServiceImpl userMasterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginValidate_Success() {
        UserMasterDTO userMasterDTO = new UserMasterDTO();
        userMasterDTO.setEmailId("test@example.com");
        userMasterDTO.setPassword("password");

        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(1);
        userMaster.setPassword("password");

        when(userMasterRepository.getUserByEmail("test@example.com")).thenReturn(userMaster);

        ResponseEntity<?> response = userMasterService.loginValidate(userMasterDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userMasterRepository, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    void testLoginValidate_Unauthorized() {
        UserMasterDTO userMasterDTO = new UserMasterDTO();
        userMasterDTO.setEmailId("test@example.com");
        userMasterDTO.setPassword("wrongpassword");

        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(1);
        userMaster.setPassword("password");

        when(userMasterRepository.getUserByEmail("test@example.com")).thenReturn(userMaster);

        ResponseEntity<?> response = userMasterService.loginValidate(userMasterDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(userMasterRepository, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    void testGetAllUsers_Success() {
        List<Object> users = new ArrayList<>();
        users.add("user1@example.com");
        users.add("user2@example.com");

        when(userMasterRepository.getAllUsers()).thenReturn(users);

        ResponseEntity<?> response = userMasterService.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userMasterRepository, times(1)).getAllUsers();
    }

    @Test
    void testSaveUserMaster() {
        UserMasterDTO userMasterDto = new UserMasterDTO();
        userMasterDto.setFullName("Test User");
        userMasterDto.setContactNo("1234567890");
        userMasterDto.setEmailId("test@example.com");
        userMasterDto.setDepartment("IT");
        userMasterDto.setDesignation("Developer");
        userMasterDto.setLocation(1);
        userMasterDto.setUserId(0);

        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(1);

        when(userMasterRepository.save(any(UserMaster.class))).thenReturn(userMaster);

        UserMaster savedUser = userMasterService.saveUserMaster(userMasterDto);

        assertEquals(userMaster, savedUser);
        verify(userMasterRepository, times(1)).save(any(UserMaster.class));
    }

    @Test
    void testDeleteUserMaster() {
        doNothing().when(userMasterRepository).deleteUserMaster(1);
        
        userMasterService.deleteUserMaster(1);
        
        verify(userMasterRepository, times(1)).deleteUserMaster(1);
    }

    @Test
    void testGetUserMasterById() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("USERID", 1);
        userMap.put("FULLNAME", "Test User");

        when(userMasterRepository.getUserMasterById(1)).thenReturn(userMap);

        Map<String, Object> result = userMasterService.getUserMasterById(1);

        assertEquals(userMap, result);
        verify(userMasterRepository, times(1)).getUserMasterById(1);
    }

    @Test
    void testUpdatePassword_Success() throws Exception {
        String passwordPayload = "{\"currentPassword\":\"oldpass\",\"newPassword\":\"newpass\",\"confirmPassword\":\"newpass\",\"emailId\":\"test@example.com\"}";
        Map<String, String> passwordData = new HashMap<>();
        passwordData.put("currentPassword", "oldpass");
        passwordData.put("newPassword", "newpass");
        passwordData.put("confirmPassword", "newpass");
        passwordData.put("emailId", "test@example.com");

        UserMaster userMaster = new UserMaster();
        userMaster.setNormalPassword("oldpass");

        when(objectMapper.readValue(passwordPayload, Map.class)).thenReturn(passwordData);
        when(userMasterRepository.getUserByEmail("test@example.com")).thenReturn(userMaster);

        ResponseEntity<?> response = userMasterService.updatePassword(passwordPayload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userMasterRepository, times(1)).updatePassword(eq("test@example.com"), eq("newpass"), any(String.class));
    }

    @Test
    void testUpdatePassword_Unauthorized() throws Exception {
        String passwordPayload = "{\"currentPassword\":\"wrongpass\",\"newPassword\":\"newpass\",\"confirmPassword\":\"newpass\",\"emailId\":\"test@example.com\"}";
        Map<String, String> passwordData = new HashMap<>();
        passwordData.put("currentPassword", "wrongpass");
        passwordData.put("newPassword", "newpass");
        passwordData.put("confirmPassword", "newpass");
        passwordData.put("emailId", "test@example.com");

        UserMaster userMaster = new UserMaster();
        userMaster.setNormalPassword("oldpass");

        when(objectMapper.readValue(passwordPayload, Map.class)).thenReturn(passwordData);
        when(userMasterRepository.getUserByEmail("test@example.com")).thenReturn(userMaster);

        ResponseEntity<?> response = userMasterService.updatePassword(passwordPayload);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(userMasterRepository, never()).updatePassword(anyString(), anyString(), anyString());
    }

    @Test
    void testUpdatePassword_UserNotFound() throws Exception {
        String passwordPayload = "{\"currentPassword\":\"oldpass\",\"newPassword\":\"newpass\",\"confirmPassword\":\"newpass\",\"emailId\":\"nonexistent@example.com\"}";
        Map<String, String> passwordData = new HashMap<>();
        passwordData.put("currentPassword", "oldpass");
        passwordData.put("newPassword", "newpass");
        passwordData.put("confirmPassword", "newpass");
        passwordData.put("emailId", "nonexistent@example.com");

        when(objectMapper.readValue(passwordPayload, Map.class)).thenReturn(passwordData);
        when(userMasterRepository.getUserByEmail("nonexistent@example.com")).thenReturn(null);

        ResponseEntity<?> response = userMasterService.updatePassword(passwordPayload);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userMasterRepository, never()).updatePassword(anyString(), anyString(), anyString());
    }

}
