package com.csmtech.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SessionResultDto;

public interface SessionResultMasterService {

	ResponseEntity<?> saveSessionResult(List<SessionResultDto> responsePayload);

}
