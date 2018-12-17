package com.app.main.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel
public class FeedbackDTO {

	@ApiModelProperty(notes = "Auto generated feedback ID", hidden = true)
	private Integer id;

	@ApiModelProperty(notes = "Source Application")
	@NotBlank(message = "Source is mandatory")
	private String source;

	@ApiModelProperty(notes = "Feedback Rating")
	@NotNull(message = "Rating is mandatory and should be between 1-5")
	@Min(value = 1, message = "Rating should be between 1-5")
	@Max(value = 5, message = "Rating should be between 1-5")
	private Integer rating;  

	@ApiModelProperty(notes = "Feedback Description")
	private String description; 

	@ApiModelProperty(notes = "User Name")
	@NotNull( message = "User Name is mandatory")
	@Pattern(regexp = "^[ A-Za-z ][ A-Za-z ][ A-Za-z][0-9]{1,5}$", message = "User name should have first 3 charaters as alphabetic and length should not be more than 8")
	private String userName;

	public FeedbackDTO() {}    

	public FeedbackDTO(Integer id, String source, int rating, String description, String userName) {  
		this.id = id;
		this.source = source;
		this.rating = rating;
		this.description = description;
		this.userName = userName;
	} 
}
