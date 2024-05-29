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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.csmtech.entity.UserMaster;
import com.csmtech.repository.UserMasterRepository;
import com.csmtech.service.LmsUserDetailsServiceImpl;

import org.slf4j.Logger;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LmsUserDetailsServiceImplTest {

    @InjectMocks
    private LmsUserDetailsServiceImpl userDetailsService;

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
    public void testLoadUserByUsername_Success() {
        when(userMasterRepository.getUserByEmail(anyString())).thenReturn(userMaster);

        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        verify(userMasterRepository, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userMasterRepository.getUserByEmail(anyString())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent@example.com");
        });
        verify(userMasterRepository, times(1)).getUserByEmail("nonexistent@example.com");
    }

    @Test
    public void testLoadUserByUsername_Exception() {
        when(userMasterRepository.getUserByEmail(anyString())).thenThrow(new RuntimeException("Database Error"));

        assertThrows(RuntimeException.class, () -> {
            userDetailsService.loadUserByUsername("test@example.com");
        });
        verify(userMasterRepository, times(1)).getUserByEmail("test@example.com");
    }
}

