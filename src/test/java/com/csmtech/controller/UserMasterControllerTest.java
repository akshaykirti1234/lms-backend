package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.UserMasterDTO;
import com.csmtech.entity.UserMaster;
import com.csmtech.service.UserMasterService;

public class UserMasterControllerTest {

    @Mock
    private UserMasterService userMasterService;

    @InjectMocks
    private UserMasterController userMasterController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testSaveUserMaster() {
        // Arrange
        UserMasterDTO userMasterDto = new UserMasterDTO();
        UserMaster userMaster = new UserMaster(); // Mocked userMaster
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(userMaster);
        when(userMasterService.saveUserMaster(any(UserMasterDTO.class))).thenReturn(userMaster);

        // Act
        ResponseEntity<?> responseEntity = userMasterController.saveUserMaster(userMasterDto);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponse.getBody(), responseEntity.getBody());
    }

    @Test
    public void testGetUserMasterList() {
        // Arrange
        List<Map<String, Object>> userMasterList = new ArrayList<>(); // Mocked userMasterList
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(userMasterList);
        when(userMasterService.getUseMasterList()).thenReturn(userMasterList);

        // Act
        ResponseEntity<?> responseEntity = userMasterController.getUseMasterList();

        // Assert
        assertEquals(expectedResponse.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponse.getBody(), responseEntity.getBody());
    }

    @Test
    public void testDeleteUserMaster() {
        // Arrange
        Integer userId = 123; // Mocked userId
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", 200);
        responseMap.put("deleted", "UserMaster is deleted successfully");
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(responseMap);
        // Mock behavior of deleteUserMaster
       // when(userMasterService.deleteUserMaster(userId)).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = userMasterController.deleteUserMaster(userId);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponse.getBody(), responseEntity.getBody());
    }

    @Test
    public void testGetUserMasterById() {
        // Arrange
        Integer userId = 123; // Mocked userId
        Map<String, Object> userMasterMap = new HashMap<>(); // Mocked userMasterMap
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(userMasterMap);
        when(userMasterService.getUserMasterById(userId)).thenReturn(userMasterMap);

        // Act
        ResponseEntity<?> responseEntity = userMasterController.getUserMasterById(userId);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponse.getBody(), responseEntity.getBody());
    }

    @Test
    public void testGetEmailList() {
        // Arrange
        List<Map<String, Object>> emailIdList = new ArrayList<>(); // Mocked emailIdList
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(emailIdList);
        when(userMasterService.gettEmailList()).thenReturn(emailIdList);

        // Act
        ResponseEntity<?> responseEntity = userMasterController.getEmaiLList();

        // Assert
        assertEquals(expectedResponse.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponse.getBody(), responseEntity.getBody());
    }

    @Test
    public void testUpdatePassword() {
    	String passwordPayload = "newPassword";
        ResponseEntity<?> expectedResult = new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
        doReturn(expectedResult).when(userMasterService).updatePassword(passwordPayload);

        // Act
        ResponseEntity<?> actualResult = userMasterController.updatePassword(passwordPayload);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}
