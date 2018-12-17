package com.app.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.main.model.Feedback;

@Repository
public interface FeedbackRepository  extends  JpaRepository<Feedback, Integer>{
}
