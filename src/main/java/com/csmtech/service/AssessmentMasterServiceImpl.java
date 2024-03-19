package com.csmtech.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.AssessmentMasterDto;
import com.csmtech.entity.AssessmentMaster;
import com.csmtech.repository.AssessmentMasterRepository;
import com.csmtech.repository.ScheduleForMasterRepository;

@Service
public class AssessmentMasterServiceImpl implements AssessmentMasterService {
	@Autowired
	private AssessmentMasterRepository assessmentMasterRepository;

	@Autowired
	ScheduleForMasterRepository scheduleForMasterRepository;

	@Override
	public AssessmentMaster saveAssessment(AssessmentMasterDto assessmentDto) {
		try {
			AssessmentMaster assessmentMaster = new AssessmentMaster();
			assessmentMaster.setAssessmentId(assessmentDto.getAssessmentId());
			assessmentMaster.setScheduleForId(assessmentDto.getScheduleForId());
			assessmentMaster.setQuestion(assessmentDto.getQuestion());
			assessmentMaster.setOption1(assessmentDto.getOption1());
			assessmentMaster.setOption2(assessmentDto.getOption2());
			assessmentMaster.setOption3(assessmentDto.getOption3());
			assessmentMaster.setOption4(assessmentDto.getOption4());
			assessmentMaster.setAnswer(assessmentDto.getAnswer());
			assessmentMaster.setCreatedBy(1);
			assessmentMaster.setCreatedOn(new Date());
			assessmentMaster.setUpdatedBy(1);
			assessmentMaster.setUpdatedOn(new Date());
			assessmentMaster.setDeletedFlag(false);

			AssessmentMaster save = assessmentMasterRepository.save(assessmentMaster);

			// Check if assessment is saved successfully
			if (save != null) {
				// Update flag in another table
				int updatedRows = scheduleForMasterRepository.updateFlagByScheduleId(assessmentDto.getScheduleForId());
				// Handle the update status if needed
			}

			return save;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> viewAssessmentData() {
		return assessmentMasterRepository.getAllAssesmentdata();

	}

	@Override
	public void deleteAssessment(Integer id) {
		assessmentMasterRepository.deleteAssessment(id);

	}

	@Override
	public Map<String, Object> getAssessmentById(Integer id) {
		return assessmentMasterRepository.updateAssessment(id);
	}
}
