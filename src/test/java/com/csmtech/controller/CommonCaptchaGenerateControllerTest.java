package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.service.CommonCaptchaGenerateService;
import com.csmtech.util.CommonCaptcha;

public class CommonCaptchaGenerateControllerTest {

    @Mock
    private CommonCaptchaGenerateService captchaService;

    @InjectMocks
    private CommonCaptchaGenerateController captchaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateCaptcha_Success() {
        // Mocking the service to return a dummy CommonCaptcha object
        CommonCaptcha dummyCaptcha = new CommonCaptcha();
        when(captchaService.generateCaptcha()).thenReturn(dummyCaptcha);

        // Calling the controller method
        ResponseEntity<?> response = captchaController.generateCaptcha();

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dummyCaptcha, response.getBody());
    }

    @Test
    public void testGenerateCaptcha_NotFound() {
        // Mocking the service to return null
        when(captchaService.generateCaptcha()).thenReturn(null);

        // Calling the controller method
        ResponseEntity<?> response = captchaController.generateCaptcha();

        // Verifying the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGenerateCaptcha_Exception() {
        // Mocking the service to throw an exception
        when(captchaService.generateCaptcha()).thenThrow(new RuntimeException("Test exception"));

        // Calling the controller method
        ResponseEntity<?> response = captchaController.generateCaptcha();

        // Verifying the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while generating captcha : Test exception", response.getBody());
    }
}
