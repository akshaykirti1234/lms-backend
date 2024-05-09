package com.csmtech.dto;



import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SessionMasterDto {
	private Integer sessionid;

	@NotNull(message = "session name shouldn't be blank.")
	private String sessionName;

	@NotNull(message = "sub module should be selected.")
	private Integer subModuleId;

	@NotNull(message = "schedule should be selected.")
	private Integer scheduleForId;

	private String video;

	private String document;

	@NotNull(message = "Description shouldn't be blank.")
	private String description;

	private Boolean isLastSession;
}
