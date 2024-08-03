package com.ust.AssessmentApi.repository;

import com.ust.AssessmentApi.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Questionsrepository extends JpaRepository<Questions,Long> {
    Questions findBySetname(String setname);

    List<Questions> findQuestionsBySetname(String setname);

}
