package com.app.main.exceptionhandler;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import com.app.main.customexception.FeedbackNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackExceptionResolverTest {

	@InjectMocks
	private FeedbackExceptionResolver feedbackExceptionResolver;

	@Mock
	private BindingResult bindingResult;

	@Mock 
	private WebRequest webRequest;

	@Mock
	private MethodParameter parameter;   
 
	@Test
	public void handleMethodArgumentNotValid() {  

		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult); 

		FieldError fieldError = new FieldError("feedback", "rating", 0, false, null, null, "rating should be more than o");

		List<FieldError> errorList = Arrays.asList(fieldError);

		Mockito.when(bindingResult.getFieldErrors()).thenReturn(errorList);

		ResponseEntity<Object> feedbackError = feedbackExceptionResolver.handleMethodArgumentNotValid(ex, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, webRequest);

		assertEquals(HttpStatus.BAD_REQUEST, feedbackError.getStatusCode());  
	 } 
 
	@Test
	public void testFeedbackNotFoundException() {

		FeedbackNotFoundException feedbackNotFoundException = new FeedbackNotFoundException(); 
		
		assertEquals(HttpStatus.NOT_FOUND, feedbackExceptionResolver.handleFeedbackNotFoundException(feedbackNotFoundException, webRequest).getStatusCode());
	}

}
