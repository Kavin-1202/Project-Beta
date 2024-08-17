package com.example.Assessment.service;

import com.example.Assessment.dto.Questionsdto;
import com.example.Assessment.dto.SetNameDto;
import com.example.Assessment.dto.Setdto;
import com.example.Assessment.model.Questions;
import com.example.Assessment.model.Set;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssessmentService {

    List<Set> getAllSets();

    Set createSet(Setdto set1);

    Set getSetById(Long setId);

    List<SetNameDto> getSet(String  setName);

    Questions updateQuestion(Long setId, Long question_id, Questionsdto qdto);

    boolean deleteQuestion(Long setId, Long question_id);
}
