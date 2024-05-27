package com.csmtech.service;

import java.util.List;

import com.csmtech.entity.ResultStatus;

public interface ResultStatusService {

	List<ResultStatus> getFinalResultByScheduleIdUserId(Integer scheduleForId, Integer userId);

	ResultStatus getResultStatusByScheduleIdUserId(Integer scheduleForId, Integer userId);

}
