package com.csmtech.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.csmtech.dto.SessionAssessmentSettingDto;
import com.csmtech.service.SessionAssessmentSettingService;

@ExtendWith(MockitoExtension.class)
class SessionAssessmentSettingControllerTest {

    @Mock
    private SessionAssessmentSettingService sessionAssessmentSettingService;

    @InjectMocks
    private SessionAssessmentSettingController controller;

    private MockMvc mockMvc;

    @Test
    void testGetApiChecked() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/getApiChecked"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hii Session Assessment Setting is working"));
    }

    @Test
    void testGetSessionforAssessmentSetting() throws Exception {
        List<Map<String, Object>> sessionList = new ArrayList<>();
        Map<String, Object> session = new HashMap<>();
        session.put("sessionId", 1);
        session.put("sessionName", "Session 1");
        sessionList.add(session);

        when(sessionAssessmentSettingService.getSessionforAssessmentSetting(anyInt())).thenReturn(sessionList);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/sessionForAssessmentSetting/{scheduleForId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sessionId").value(1))
                .andExpect(jsonPath("$[0].sessionName").value("Session 1"));
    }

    @Test
    void testSaveSessionAssessmentSetting() throws Exception {
        SessionAssessmentSettingDto dto = new SessionAssessmentSettingDto();
        dto.setSchedule(1);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(post("/sessionAssessmentSetting")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"scheduleForId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSessionAssessmentSetting() throws Exception {
        List<Map<String, Object>> sessionAssessmentSetting = new ArrayList<>();
        Map<String, Object> setting = new HashMap<>();
        setting.put("settingId", 1);
        setting.put("settingName", "Setting 1");
        sessionAssessmentSetting.add(setting);

        when(sessionAssessmentSettingService.getSessionAssessmentSetting()).thenReturn(sessionAssessmentSetting);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/sessionAssessmentSetting"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].settingId").value(1))
                .andExpect(jsonPath("$[0].settingName").value("Setting 1"));
    }

    
}

