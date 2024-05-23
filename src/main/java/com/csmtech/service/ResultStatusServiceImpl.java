package com.csmtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.entity.ResultStatus;
import com.csmtech.repository.ResultStatusRepository;

@Service
public class ResultStatusServiceImpl implements ResultStatusService {

	@Autowired
	ResultStatusRepository resultStatusRepository;
	
	@Override
	public List<ResultStatus> getFinalResultByScheduleIdUserId(Integer scheduleForId, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
