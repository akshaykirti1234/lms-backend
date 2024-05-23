package com.csmtech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.entity.ResultStatus;
import com.csmtech.repository.ResultStatusRepository;

@Service
public class ResultStatusServiceImpl implements ResultStatusService {

	@Autowired
	ResultStatusRepository resultStatusRepository;
	
	private static final Logger logger=LoggerFactory.getLogger(ResultStatusServiceImpl.class);
	
	@Override
	public List<ResultStatus> getFinalResultByScheduleIdUserId(Integer scheduleForId, Integer userId) {
		logger.info("getFinalResultByScheduleIdUserId method of ResultStatusServiceImpl is executed");
		return resultStatusRepository.getFinalResultByScheduleIdUserId(scheduleForId,userId);

}

	@Override
	public ResultStatus getResultStatusByScheduleIdUserId(Integer scheduleForId, Integer userId) {
		logger.info("getResultStatusByScheduleIdUserId method of ResultStatusServiceImpl is executed");
		return resultStatusRepository.getResultStatusByScheduleIdUserId(scheduleForId,userId);
	}
}
