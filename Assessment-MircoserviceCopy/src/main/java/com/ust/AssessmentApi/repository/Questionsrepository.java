package com.ust.AssessmentApi.repository;

import com.ust.AssessmentApi.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public interface Questionsrepository extends JpaRepository<Questions,Long> {

    List<Questions> findBySetId(Long setId);

}
