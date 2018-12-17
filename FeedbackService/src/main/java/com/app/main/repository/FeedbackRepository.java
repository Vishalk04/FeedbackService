package com.app.main.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.main.model.Feedback;

@Repository
public interface FeedbackRepository  extends  CrudRepository<Feedback, Integer>{
}
