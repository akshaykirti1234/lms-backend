package com.csmtech.util;

import org.springframework.stereotype.Component;

@Component
public class CommonCaptcha {

	private String id;

	private String text;
	
	
	
	public CommonCaptcha() {
		
	}

	public CommonCaptcha(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
