package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.UserTranInfoDto;
import com.csmtech.service.UserTranInfoService;

public class UserTranInfoControllerTest {

    @Mock
    private UserTranInfoService userTranInfoService;

    @InjectMocks
    private UserTranInfoController userTranInfoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this); // Initialize mocks
    }

    @Test
    public void testSaveUserInfo() {
    	
    	UserTranInfoDto userTranInfoDto = new UserTranInfoDto();
        ResponseEntity<?> expectedResult = new ResponseEntity<>("User Info Saved Successfully", HttpStatus.OK);
        doReturn(expectedResult).when(userTranInfoService).saveUserInfo(userTranInfoDto);

        // Act
        ResponseEntity<?> actualResult = userTranInfoController.saveUserInfo(userTranInfoDto);

        // Assert
        assertEquals(expectedResult, actualResult);
    	
    	
    	
    }
}
