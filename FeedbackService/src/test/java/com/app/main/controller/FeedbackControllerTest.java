package com.app.main.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.app.main.FeedbackServiceApplication;
import com.app.main.repository.FeedbackRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FeedbackServiceApplication.class)
@SpringBootTest
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class FeedbackControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test 
	public void verifyPostFeedback() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/feedback").contentType(MediaType.APPLICATION_JSON).content("{  \"description\": \"string\", \"rating\": 3,\"source\": \"yash\",\"userName\": \"usr121\"}")
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.source").exists())
		.andExpect(jsonPath("$.userName").exists())
		.andExpect(jsonPath("$.rating").exists())
		.andExpect(jsonPath("$.source").value("yash"))
		.andExpect(jsonPath("$.userName").value("usr121")); 
	} 

	@Test
	public void verifyAllFedback() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/feedback").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2))).andDo(print());
	}

	@Test
	public void verifyNullAllFeedback() throws Exception { 
		feedbackRepository.deleteAll();
		mockMvc.perform(MockMvcRequestBuilders.get("/feedback").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is(404))
		.andExpect(status().reason("Feedback does not exist"))
		.andDo(print());
	}

	@Test 
	public void verifyGetFeedbackById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/feedback/1").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("id").exists())
		.andExpect(jsonPath("source").exists())
		.andExpect(jsonPath("userName").exists())
		.andExpect(jsonPath("rating").exists())
		.andExpect(jsonPath("id").value(1))
		.andExpect(jsonPath("source").value("yash"))
		.andExpect(jsonPath("userName").value("usr123"))
		.andDo(print());
	}	

	@Test 
	public void verifyNullFeedbackById() throws Exception { 
		mockMvc.perform(MockMvcRequestBuilders.get("/feedback/11").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is(404))
		.andExpect(status().reason("Feedback does not exist"))
		.andDo(print());
	}
}
