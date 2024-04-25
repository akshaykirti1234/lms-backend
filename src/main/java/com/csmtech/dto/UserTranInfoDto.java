package com.csmtech.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserTranInfoDto {
//	{"userId":"18","sessionId":9,"startTime":"2024-04-24T04:46:50.448Z","endTime":"2024-04-24T04:47:00.662Z"}
	private Integer userId;
	private Integer sessionId;
	private Date startTime;
	private Date endTime;
	private Integer rating;
}
