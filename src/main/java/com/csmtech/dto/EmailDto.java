package com.csmtech.dto;

import java.util.List;

import lombok.Data;

@Data
public class EmailDto {

	private String notifyStatus;
    private List<String> selectedEmail;
    private String description;
	
}
