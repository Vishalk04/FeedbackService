
package com.app.main.model;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackDTOTest {

	private Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}  
 
	@Test
	public void shouldNotAcceptSourceNameIfNotGiven() {

		FeedbackDTO feedback = new FeedbackDTO(1, "", 1, "test case having empty source", "YAS123");

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedback);

		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
	}

	@Test
	public void shouldAcceptSourceNameIfGiven() {

		FeedbackDTO feedbackDTO = new FeedbackDTO(1, "source", 1, "test case having empty source", "YAS123");

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedbackDTO);

		assertTrue(violations.isEmpty());
		assertEquals(violations.size(), 0);
	}

	@Test
	public void shouldNotAcceptRatingIfNotGiven() {

		FeedbackDTO feedbackDTO = new FeedbackDTO(1, "source", 1, "test case having empty rating", "YAS123");
		feedbackDTO.setRating(null);

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedbackDTO);

		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
	}

	@Test
	public void shouldNotAcceptRatingIfRatingIsLessThanOne() {

		FeedbackDTO feedbackDTO = new FeedbackDTO(1, "source", 0, "test case having rating less than one", "YAS123");

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedbackDTO);

		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
	}

	@Test
	public void shouldNotAcceptRatingIfRatingIsMoreThanFive() {

		FeedbackDTO feedbackDTO = new FeedbackDTO(1, "source", 6, "test case having rating more than 5", "YAS123");

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedbackDTO);

		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
	}

	@Test
	public void shouldAcceptRatingIfRatingBetweenOneToFive() {

		FeedbackDTO feedbackDTO = new FeedbackDTO(1, "source", 4, "test case having rating more than 5", "YAS123");

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedbackDTO);

		assertTrue(violations.isEmpty());
		assertEquals(violations.size(), 0);
	}

	@Test
	public void shouldNotAcceptUserNameIfNotGiven() {

		FeedbackDTO feedbackDTO = new FeedbackDTO(1, "source", 1, "test case having empty rating", "YAS123");
		feedbackDTO.setUserName(null);

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedbackDTO);

		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
	} 

	@Test
	public void shouldNotAcceptUserNameIfFirstThreePlacesAreNumber() {

		FeedbackDTO feedbackDTO = new FeedbackDTO(1, "source", 1, "test case having empty rating", "1234123");

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedbackDTO);

		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
	}

	@Test
	public void shouldNotAcceptUserNameIfLengthMoreThanEight() {

		FeedbackDTO feedbackDTO = new FeedbackDTO(1, "source", 1, "test case having empty rating", "YAS123456");

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedbackDTO);

		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
	}

	@Test
	public void shouldAcceptUserNameIfLengthLessOrEqualToEightAndFirstThreePlacesAreAlphabets() {

		FeedbackDTO feedbackDTO = new FeedbackDTO(1, "source", 1, "test case having empty rating", "YAS12345");

		Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(feedbackDTO);

		assertTrue(violations.isEmpty());
		assertEquals(violations.size(), 0);
	}
}
