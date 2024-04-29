package com.csmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmtech.entity.SessionAssessmentSetting;

public interface SessionAssessmentSettingRepository extends JpaRepository<SessionAssessmentSetting, Integer> {

	SessionAssessmentSetting findFirst1BySessionMaster_SessionId(Integer sessionId);

}
