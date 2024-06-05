package com.csmtech.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.TopicMasterDto;
import com.csmtech.entity.TopicMaster;
import com.csmtech.service.TopicMasterService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class TopicMasterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TopicMasterService topicMasterService;

    @InjectMocks
    private TopicMasterController topicMasterController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(topicMasterController).build();
    }

    @Test
    public void testSaveTopic() throws Exception {
        TopicMasterDto topicDto = new TopicMasterDto();
        TopicMaster savedTopic = new TopicMaster();

        when(topicMasterService.saveTopic(any(TopicMasterDto.class))).thenReturn(savedTopic);

        mockMvc.perform(post("/api/topic/saveTopic")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(topicDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(savedTopic)));
    }

    @Test
    public void testViewTopicData() throws Exception {
        TopicMaster topicMaster = new TopicMaster();

        when(topicMasterService.viewTopicData()).thenReturn(Collections.singletonList(topicMaster));

        mockMvc.perform(get("/api/topic/getTopic"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Arrays.asList(topicMaster))));
    }

    @Test
    public void testGetTopicById() throws Exception {
        TopicMaster topicMaster = new TopicMaster();

        when(topicMasterService.getTopicById(1)).thenReturn(topicMaster);

        mockMvc.perform(get("/api/topic/getTopic/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(topicMaster)));
    }

    @Test
    public void testDeleteTopic() throws Exception {
        doNothing().when(topicMasterService).deleteTopic(1);

        mockMvc.perform(delete("/api/topic/deleteTopic/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"deleted\"}"));
    }

    @Test
    public void testGetTopicByUserIdAndScheduleId() throws Exception {
        TopicMaster topicMaster = new TopicMaster();

        when(topicMasterService.getTopicByUserIdAndScheduleId(4, 11)).thenReturn(topicMaster);

        mockMvc.perform(get("/api/topic/getTopicByUserIdAndScheduleId/4/11"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(topicMaster)));
    }

    @Test
    public void testSaveRecordedTopic() throws Exception {
        TopicMaster savedTopicMaster = new TopicMaster();
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some file content".getBytes());

        when(topicMasterService.saveRecordedTopic(any(MultipartFile.class), any(Integer.class))).thenReturn(savedTopicMaster);

        mockMvc.perform(multipart("/api/topic/saveRecordedTopic")
                .file(file)
                .param("topicId", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(savedTopicMaster)));
    }

}
