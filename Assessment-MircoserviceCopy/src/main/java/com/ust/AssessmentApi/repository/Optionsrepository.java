package com.ust.AssessmentApi.repository;

import com.ust.AssessmentApi.model.Options;
import com.ust.AssessmentApi.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Optionsrepository extends JpaRepository<Options,Long> {

}
