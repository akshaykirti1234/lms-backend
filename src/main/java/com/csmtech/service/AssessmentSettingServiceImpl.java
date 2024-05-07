package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.AssessmentSettingDto;
import com.csmtech.dto.AssessmentSettingScheduleDto;
import com.csmtech.entity.AssessmentSetting;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.repository.AssessmentSettingRespository;

@Service
public class AssessmentSettingServiceImpl implements AssessmentSettingService {

	@Autowired
	private AssessmentSettingRespository assessmentSettingRespository;
	
	@Override
	public AssessmentSetting saveAssessmentSetting(AssessmentSettingDto assessmentSettingDto) {
		
		AssessmentSetting assessmentSetting=null;
		
		ModuleMaster moduleMaster=new ModuleMaster();
		moduleMaster.setModuleId(assessmentSettingDto.getModule());
		
		SubModule subModule=new SubModule();
		subModule.setSubmoduleId(assessmentSettingDto.getSubModule());
		
		ScheduleForMaster scheduleForMaster=new ScheduleForMaster();
	
		
		
       
        
        for (AssessmentSettingScheduleDto scheduleDto : assessmentSettingDto.getScheduleWiseList()) {
        	
        	 assessmentSetting = new AssessmentSetting();
        	 
        	 assessmentSetting.setModuleMaster(moduleMaster);
             assessmentSetting.setSubModule(subModule);
         	 scheduleForMaster.setScheduleForId(scheduleDto.getScheduleForId());
             assessmentSetting.setScheduleForMaster(scheduleForMaster);
             assessmentSetting.setNumberOfQuestion(scheduleDto.getNumberOfQuestions());
             assessmentSetting.setPassingPercentage(scheduleDto.getPassingPercentage());
             assessmentSettingRespository.save(assessmentSetting);
        }

        
        return assessmentSetting;
  
	}

	@Override
	public List<Map<String, Object>> getAssessmentSetting() {
		return assessmentSettingRespository.getAssessmentSetting();
	}

	@Override
	public Map<String, Object> getAssessmentSettingById(Integer assessmentSettingId) {
		return assessmentSettingRespository.getAssessmentSettingById(assessmentSettingId);
	}

	@Override
	public void deleteAssessmentSetting(Integer assessmentSettingId) {
		assessmentSettingRespository.deleteAssessmentSetting(assessmentSettingId);
	}

	@Override
	public void updateAssessmentSetting(Integer assessmentSettingId, Integer noOfQuestions , Double passingPercentage) {
		assessmentSettingRespository.updateAssessmentSetting(assessmentSettingId,noOfQuestions,passingPercentage);
	}

	@Override
	public List<Map<String, Object>> getScheduleforAssessmentSetting(Integer submoduleId) {
		return assessmentSettingRespository.getScheduleforAssessmentSetting(submoduleId);
	}

}
