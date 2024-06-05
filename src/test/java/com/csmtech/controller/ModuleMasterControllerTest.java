package com.csmtech.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.csmtech.dto.ModuleMasterDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.service.ModuleMasterService;
@SpringBootTest
public class ModuleMasterControllerTest {

    @MockBean
    private ModuleMasterService moduleMasterService;

    @InjectMocks
    private ModuleMasterController moduleMasterController;

    private MockMvc mockMvc;
    
    @Value("${actuallogofile.path}")
    private String actualFilePath;

    @Value("${templogofile.path}")
    private String tempPath;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(moduleMasterController).build();
        File testFile = new File(actualFilePath, "KT.png");
        try (OutputStream os = new FileOutputStream(testFile)) {
            os.write("test content".getBytes());
        }
    }

    @Test
    public void testSaveModuleMaster() throws Exception {
        ModuleMasterDto moduleMasterDto = new ModuleMasterDto();
        moduleMasterDto.setLogo("testlogo.png");

        ModuleMaster moduleMaster = new ModuleMaster();
        moduleMaster.setModuleId(1);

        when(moduleMasterService.saveModule(any(ModuleMasterDto.class))).thenReturn(moduleMaster);

        mockMvc.perform(post("/module")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"logo\":\"testlogo.png\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moduleId").value(1));
    }

    @Test
    public void testGetModule() throws Exception {
        List<ModuleMaster> moduleList = new ArrayList<>();
        moduleList.add(new ModuleMaster());

        when(moduleMasterService.getModuleMaster()).thenReturn(moduleList);

        mockMvc.perform(get("/module"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void testGetModuleById() throws Exception {
        ModuleMaster moduleMaster = new ModuleMaster();
        moduleMaster.setModuleId(1);

        when(moduleMasterService.getModuleById(1)).thenReturn(moduleMaster);

        mockMvc.perform(get("/module/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moduleId").value(1));
    }

    @Test
    public void testDeleteModule() throws Exception {
        doNothing().when(moduleMasterService).deleteModuleById(1);

        mockMvc.perform(delete("/module/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value("module is deleted successfuly"));
    }

//    @Test
//    public void testSetTempFile() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("file", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
//
//        mockMvc.perform(multipart("/setlogo").file(file))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.fileName").value("test.png"));
//    }

//    @Test
//    public void testDownloadDocumentSuccess() throws Exception {
//        String fileName = "KT.png";
//
//        MockHttpServletResponse response = mockMvc.perform(get("/viewLogo/{fileName}", fileName))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().contentType(MediaType.IMAGE_PNG))
//                .andReturn().getResponse();
//
//        // Additional assertions
//        assert response.getContentAsByteArray().length > 0;
//        assert response.getHeader("content-disposition").equals("inline;filename=" + fileName);
//    }
    
    @Test
    public void testDownloadDocumentFileNotFound() throws Exception {
        mockMvc.perform(get("/viewLogo/{fileName}", "nonexistent.png"))
                .andExpect(status().isInternalServerError());
    }
}
