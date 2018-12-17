package com.app.main.exceptionhandler;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

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
	public void testHandleNoContent() {

		NoSuchElementException noSuchElementException = new NoSuchElementException(); 
		
		assertEquals(HttpStatus.NOT_FOUND, feedbackExceptionResolver.handleNoSuchElementException(noSuchElementException, webRequest).getStatusCode());
	}

	@Test
	public void testHandleSQLException() {

		SQLException sqlException = new SQLException();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, feedbackExceptionResolver.handleSQLException(sqlException, webRequest).getStatusCode());
	}
}
