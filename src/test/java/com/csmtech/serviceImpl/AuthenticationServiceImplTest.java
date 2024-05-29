package com.csmtech.serviceImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.entity.UserMaster;
import com.csmtech.repository.UserMasterRepository;
import com.csmtech.service.AuthenticationServiceImpl;

import org.slf4j.Logger;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserMasterRepository userMasterRepository;

    @Mock
    private Logger logger;

    private UserMaster userMaster;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userMaster = new UserMaster();
        userMaster.setEmailId("test@example.com");
        userMaster.setPassword("password");
    }

    @Test
    public void testFindByEmail_Success() {
        when(userMasterRepository.getUserByEmail(anyString())).thenReturn(userMaster);

        UserMaster result = authenticationService.findByEmail("test@example.com");
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmailId());
        verify(userMasterRepository, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    public void testFindByEmail_NotFound() {
        when(userMasterRepository.getUserByEmail(anyString())).thenReturn(null);

        UserMaster result = authenticationService.findByEmail("nonexistent@example.com");
        assertNull(result);
        verify(userMasterRepository, times(1)).getUserByEmail("nonexistent@example.com");
    }

    @Test
    public void testFindByEmail_Exception() {
        when(userMasterRepository.getUserByEmail(anyString())).thenThrow(new RuntimeException("Database Error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authenticationService.findByEmail("test@example.com");
        });

        assertEquals("Database Error", exception.getMessage());
        verify(userMasterRepository, times(1)).getUserByEmail("test@example.com");
    }
}
