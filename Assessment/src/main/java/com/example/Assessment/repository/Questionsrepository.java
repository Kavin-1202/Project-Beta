package com.example.Assessment.repository;


import com.example.Assessment.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Questionsrepository extends JpaRepository<Questions,Long> {

    List<Questions> findBySetId(Long setId);

}
