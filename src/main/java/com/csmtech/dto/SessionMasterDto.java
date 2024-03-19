package com.csmtech.dto;

import lombok.Data;

@Data
public class SessionMasterDto {
	private Integer sessionid;
	private String sessionName;
	private Integer subModuleId;
	private Integer scheduleForId;
	private String video;
	private String document;
	private String description;
	private Boolean isLastSession;
}
