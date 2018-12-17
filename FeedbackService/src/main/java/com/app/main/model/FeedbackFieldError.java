package com.app.main.model;

import lombok.Getter;

@Getter
public class FeedbackFieldError {

	private String field;
	private Object rejectedValue;
	private String description;

	public FeedbackFieldError(String field, Object rejectedValue, String description) {
		super();
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.description = description; 
	}
}
