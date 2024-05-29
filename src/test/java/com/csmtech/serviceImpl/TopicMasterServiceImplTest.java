package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.TopicMasterDto;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.TopicMaster;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.TopicMasterRepository;
import com.csmtech.service.TopicMasterServiceImpl;
import com.csmtech.util.EmailServiceUtil;
import com.csmtech.util.FileUpload;

public class TopicMasterServiceImplTest {

    @InjectMocks
    private TopicMasterServiceImpl topicMasterService;

    @Mock
    private TopicMasterRepository topicMasterRepository;

    @Mock
    private EmailServiceUtil emailServiceUtil;

    @Mock
    private MultipartFile file;

    private TopicMasterDto topicDto;
    private TopicMaster topicMaster;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        topicDto = new TopicMasterDto();
        topicDto.setTopicId(1);
        topicDto.setTopicName("Test Topic");
        topicDto.setReferTo("test@example.com");
        topicDto.setUserId(1);
        topicDto.setScheduleForId(1);

        topicMaster = new TopicMaster();
        topicMaster.setTopicId(1);
        topicMaster.setTopicName("Test Topic");
        topicMaster.setReferTo("test@example.com");

        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(1);
        topicMaster.setUserMaster(userMaster);

        ScheduleForMaster scheduleForMaster = new ScheduleForMaster();
        scheduleForMaster.setScheduleForId(1);
        topicMaster.setScheduleForMaster(scheduleForMaster);
    }

    @Test
    public void testSaveTopic_Success() {
        when(topicMasterRepository.save(any(TopicMaster.class))).thenReturn(topicMaster);

        TopicMaster result = topicMasterService.saveTopic(topicDto);

        assertNotNull(result);
        assertEquals(topicMaster.getTopicId(), result.getTopicId());
        verify(topicMasterRepository, times(1)).save(any(TopicMaster.class));
    }

    @Test
    public void testViewTopicData() {
        List<TopicMaster> topicList = Arrays.asList(topicMaster);
        when(topicMasterRepository.viewTopicData()).thenReturn(topicList);

        List<TopicMaster> result = topicMasterService.viewTopicData();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(topicMasterRepository, times(1)).viewTopicData();
    }

    @Test
    public void testDeleteTopic() {
        Integer topicId = 1;
        topicMasterService.deleteTopic(topicId);
        verify(topicMasterRepository, times(1)).deleteTopic(topicId);
    }

    @Test
    public void testGetTopicById() {
        Integer topicId = 1;
        when(topicMasterRepository.getTopicById(topicId)).thenReturn(topicMaster);

        TopicMaster result = topicMasterService.getTopicById(topicId);

        assertNotNull(result);
        assertEquals(topicId, result.getTopicId());
        verify(topicMasterRepository, times(1)).getTopicById(topicId);
    }

    @Test
    public void testGetTopicByUserIdAndScheduleId() {
        Integer userId = 1;
        Integer scheduleForId = 1;
        when(topicMasterRepository.getTopicByUserIdAndScheduleId(userId, scheduleForId)).thenReturn(topicMaster);

        TopicMaster result = topicMasterService.getTopicByUserIdAndScheduleId(userId, scheduleForId);

        assertNotNull(result);
        assertEquals(userId, result.getUserMaster().getUserId());
        assertEquals(scheduleForId, result.getScheduleForMaster().getScheduleForId());
        verify(topicMasterRepository, times(1)).getTopicByUserIdAndScheduleId(userId, scheduleForId);
    }

    @Test
    public void testSaveRecordedTopic_Success() throws Exception {
        String uploadedFilePath = "uploaded/file/path";
        when(FileUpload.uploadFile(file)).thenReturn(uploadedFilePath);
        when(topicMasterRepository.findById(anyInt())).thenReturn(Optional.of(topicMaster));
        when(topicMasterRepository.save(any(TopicMaster.class))).thenReturn(topicMaster);

        TopicMaster result = topicMasterService.saveRecordedTopic(file, 1);

        assertNotNull(result);
        assertEquals(uploadedFilePath, result.getVideoPath());
        verify(topicMasterRepository, times(1)).save(any(TopicMaster.class));
        verify(emailServiceUtil, times(1)).sendRecording(anyString(), anyString());
    }

    @Test
    public void testSaveRecordedTopic_Failure() throws Exception {
        when(FileUpload.uploadFile(file)).thenThrow(new RuntimeException("File upload error"));
        when(topicMasterRepository.findById(anyInt())).thenReturn(Optional.of(topicMaster));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            topicMasterService.saveRecordedTopic(file, 1);
        });

        assertEquals("Failed to save recorded topic and send email", thrown.getMessage());
        verify(topicMasterRepository, never()).save(any(TopicMaster.class));
        verify(emailServiceUtil, never()).sendRecording(anyString(), anyString());
    }
}
