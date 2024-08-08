package com.example.Assessment.service;

import com.example.Assessment.dto.Questionsdto;
import com.example.Assessment.dto.SetNameDto;
import com.example.Assessment.dto.Setdto;
import com.example.Assessment.model.*;
import com.example.Assessment.repository.Optionsrepository;
import com.example.Assessment.repository.Questionsrepository;
import com.example.Assessment.repository.Setrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceImplementation implements AssessmentService {

    @Autowired
    private Optionsrepository optionsrepository;

    @Autowired
    private Questionsrepository questionsRepository;

    @Autowired
    private Setrepository setrepository;


    public List<Set> getAllSets() {
        return setrepository.findAll();
    }

    public Set createSet(Setdto set1) {
        Set set = new Set();
        set.setSetId(set1.getSetId());
        set.setSetName(set1.getSetName());
        set.setDomain(set1.getDomain());
        set.setCreatedby(Person.getName());
        set.setStatus(Status.PENDING);
        set.setCreatedTimestamp(new Date());
        set.setUpdatedTimestamp(null);
        List<Questions> questionEntities = set1.getQuestionList().stream()
                .map(questions -> {
                    Questions questionEntity = new Questions();
                    questionEntity.setSetId(set1.getSetId());
                    questionEntity.setQuestion_description(questions.getQuestion_description());
                    return questionEntity;
                })
                .collect(Collectors.toList());
        questionsRepository.saveAll(questionEntities);
        set.setQuestionList(questionEntities);
        setrepository.save(set);
        return set;

    }

    public List<SetNameDto> getSet(String  setName) {
        Set set = setrepository.findBySetNameIgnoreCase(setName).orElse(null);
        List<Questions> questions= set.getQuestionList();

        return questions.stream().map(question -> {
            SetNameDto setNameDto = new SetNameDto();
            setNameDto.setQuestion_id(question.getQuestion_id());
            setNameDto.setQuestion_description(question.getQuestion_description());
            return setNameDto;
        }).collect(Collectors.toList());
    }



    public List<Questions> updateQuestion(Long setId, Long question_id, Questionsdto qdto) {
        Set set = setrepository.findById(setId).orElse(null);
        set.setUpdatedTimestamp(new Date());
        set.setUpdated_by(Person.getName());
        List<Questions> questions = questionsRepository.findBySetId(setId);

        for (Questions question : questions) {
            if (question.getQuestion_id() == question_id) {

                List<Options> existingOptions = question.getOptions();
                if (existingOptions == null) {
                    existingOptions = new ArrayList<>();
                }


                List<Options> newOptionsEntities = qdto.getOptionsdtoList().stream()
                        .map(option -> {
                            Options optionsEntity = new Options();
                            optionsEntity.setQuestion_id(question_id);
                            optionsEntity.setAnswer(option.getAnswer());
                            optionsEntity.setSuggestion(option.getSuggestion());
                            return optionsEntity;
                        })
                        .collect(Collectors.toList());

                // Add new options to existing options list
                existingOptions.addAll(newOptionsEntities);

                // Save all new options (both existing and newly added)
                optionsrepository.saveAll(newOptionsEntities);

                // Update the question's options with the merged list
                question.setOptions(existingOptions);
            }
        }

        // Save updated questions to database
        questionsRepository.saveAll(questions);

        return questions;
    }



    public boolean deleteQuestion(Long setId, Long question_id) {
        Set set = setrepository.findById(setId).orElse(null);
        List<Questions> questions = set.getQuestionList();
        for(Questions question : questions) {
            if(question.getQuestion_id() == question_id) {
                questions.remove(question);
                set.setQuestionList(questions);
                setrepository.save(set);
                questionsRepository.deleteById(question_id);
                return true;
            }
        }
        return false;
    }

    public Set getSetById(Long setId) {

        return setrepository.findById(setId).orElse(null);
    }
}


