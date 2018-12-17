package com.app.main.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.main.customexception.FeedbackNotFoundException;
import com.app.main.model.FeedbackFieldError;

@ControllerAdvice
public class FeedbackExceptionResolver extends ResponseEntityExceptionHandler{ 
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers, 
			HttpStatus status,
			WebRequest request  
			) { 
 
		BindingResult bindingResult = ex.getBindingResult();  

		List<FeedbackFieldError> feedbackFieldErrors = bindingResult.getFieldErrors().stream()
				.map(fieldError -> new FeedbackFieldError(
						fieldError.getField(),
						fieldError.getRejectedValue(), 
						fieldError.getDefaultMessage())
						) 
				.collect(Collectors.toList());  

		return new ResponseEntity<>(feedbackFieldErrors, HttpStatus.BAD_REQUEST);
	}  
	
	@ExceptionHandler(value = FeedbackNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Feedback does not exist")
	public ResponseEntity<?> handleFeedbackNotFoundException(FeedbackNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	}  
}


