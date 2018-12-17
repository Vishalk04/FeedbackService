package com.app.main.customexception;

public class FeedbackNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FeedbackNotFoundException() {
		super("Feedback Not found");
	}
}
