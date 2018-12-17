package com.app.main.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.app.main.model.Feedback;
import com.app.main.model.FeedbackDTO;
import com.app.main.repository.FeedbackRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "/feedback", produces = "application/json")
public class FeedbackController {

	@Autowired
	private FeedbackRepository feedbackRepository;

	private static final ModelMapper modelMapper = new ModelMapper(); 

	@ApiOperation(value = "Get all User feedback", response = Feedback.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrieve All Feedback", response = Feedback.class),
			@ApiResponse(code = 204, message = "Feedback details not available"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "Feedback Not found") })

	@GetMapping(value = "/feedback", produces = "application/json")
	public ResponseEntity<List<Resource<FeedbackDTO>>> getAllFeedback() throws NoSuchElementException, SQLException, Exception {

		List<Feedback> feedbacks = (List<Feedback>) feedbackRepository.findAll();

		if(feedbacks.size() == 0) {
			throw new NoSuchElementException();
		} 

		List<FeedbackDTO> feedbacksDTO =  feedbacks.stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
				.collect(Collectors.toList());

		final List<Resource<FeedbackDTO>> feedbackResource = feedbacksDTO.stream().map(Resource::new) 
				.collect(Collectors.toList()); 

		for (Resource<FeedbackDTO> resource : feedbackResource) {
			resource.add(linkTo(methodOn(FeedbackController.class).getFeedbackById(resource.getContent().getId()))
					.withSelfRel());
		} 

		return ResponseEntity.ok(feedbackResource); 
	}


	@ApiOperation(value = "Post User feedback", response = Feedback.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Saved Feedback", response = Feedback.class),
			@ApiResponse(code = 400, message = "Bad Feedback Request"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "Feedback Not found") })

	@PostMapping(value = "/feedback", consumes = "application/json", produces = "application/json")
	public ResponseEntity<FeedbackDTO> postFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) throws  SQLException {

		Feedback feedback = feedbackRepository.save(modelMapper.map(feedbackDTO, Feedback.class));

		return ResponseEntity.ok(modelMapper.map(feedback, FeedbackDTO.class));
	}


	@ApiOperation(value = "Get User feedback by id", response = Feedback.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrieve Feedback ", response = Feedback.class),
			@ApiResponse(code = 204, message = "Feedback details not available"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "Feedback Not found") })

	@GetMapping(value = "feedback/{id}", produces = "application/json")
	public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable(value = "id", required = true) Integer id)
			throws NoSuchElementException, SQLException {

		Feedback feedback = feedbackRepository.findById(id).get();

		return ResponseEntity.ok(modelMapper.map(feedback, FeedbackDTO.class));
	}
}
