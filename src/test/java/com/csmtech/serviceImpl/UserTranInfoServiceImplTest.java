package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.UserTranInfoDto;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.UserMaster;
import com.csmtech.entity.UserTranInfo;
import com.csmtech.repository.UserTranInfoRepository;
import com.csmtech.service.UserTranInfoServiceImpl;

@SpringBootTest
public class UserTranInfoServiceImplTest {
	
	@Mock
    private UserTranInfoRepository userTranInfoRepository;

    @InjectMocks
    private UserTranInfoServiceImpl userTranInfoService;

    private UserTranInfoDto userTranInfoDto;
    private UserTranInfo userTranInfo;

    @BeforeEach
    void setUp() {
    	userTranInfoDto = new UserTranInfoDto();
        userTranInfoDto.setUserId(18);
        userTranInfoDto.setSessionId(9);
        userTranInfoDto.setStartTime(new Date());
        userTranInfoDto.setEndTime(new Date());
        userTranInfoDto.setRating(5);

        userTranInfo = new UserTranInfo();
        userTranInfo.setUserMaster(new UserMaster());
        userTranInfo.getUserMaster().setUserId(18);
        userTranInfo.setSessionMaster(new SessionMaster());
        userTranInfo.getSessionMaster().setSessionId(9);
        userTranInfo.setStartTime(userTranInfoDto.getStartTime());
        userTranInfo.setEndTime(userTranInfoDto.getEndTime());
        userTranInfo.setRating(userTranInfoDto.getRating());
        userTranInfo.setUserTransId(1);
    }

    @Test
    void testSaveUserInfo_Success() {
        when(userTranInfoRepository.save(any(UserTranInfo.class))).thenReturn(userTranInfo);

        ResponseEntity<?> response = userTranInfoService.saveUserInfo(userTranInfoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userTranInfoRepository, times(1)).save(any(UserTranInfo.class));
    }

    @Test
    void testSaveUserInfo_Failure_InvalidDto() {
        userTranInfoDto.setUserId(null);

        ResponseEntity<?> response = userTranInfoService.saveUserInfo(userTranInfoDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userTranInfoRepository, never()).save(any(UserTranInfo.class));
    }

    @Test
    void testSaveUserInfo_InternalServerError() {
        when(userTranInfoRepository.save(any(UserTranInfo.class))).thenReturn(new UserTranInfo());

        ResponseEntity<?> response = userTranInfoService.saveUserInfo(userTranInfoDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(userTranInfoRepository, times(1)).save(any(UserTranInfo.class));
    }

    @Test
    void testSaveUserInfo_Exception() {
        when(userTranInfoRepository.save(any(UserTranInfo.class))).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = userTranInfoService.saveUserInfo(userTranInfoDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(userTranInfoRepository, times(1)).save(any(UserTranInfo.class));
    }

}
