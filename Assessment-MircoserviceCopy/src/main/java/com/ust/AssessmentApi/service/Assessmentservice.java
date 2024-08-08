package com.ust.AssessmentApi.service;

import com.ust.AssessmentApi.dto.Questionsdto;
import com.ust.AssessmentApi.dto.Setdto;
import com.ust.AssessmentApi.model.*;
import com.ust.AssessmentApi.repository.Optionsrepository;
import com.ust.AssessmentApi.repository.Questionsrepository;
import com.ust.AssessmentApi.repository.Setrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Assessmentservice {

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
        set.setSet_description(set1.getSet_description());
        set.setDomain(set1.getDomain());
        set.setCreatedby(Person.getName());
        set.setStatus(Status.INITIATED);
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

    public Set getSet(Long setId) {
        return setrepository.findById(setId).orElse(null);
    }

    public List<Questions> updateQuestion(Long setId, Long question_id, Questionsdto qdto) {
        Set set = setrepository.findById(setId).orElse(null);
        set.setUpdatedTimestamp(new Date());
        List<Questions> questions = questionsRepository.findBySetId(setId);
        for( Questions question:questions){
            if (question.getQuestion_id()==question_id) {
                question.setQuestion_description(qdto.getQuestion_description());
                List<Options> optionsEntities = qdto.getOptionsdtoList().stream()
                        .map(option -> {
                            Options optionsEntity = new Options();
                            optionsEntity.setQuestion_id(question_id);
                            optionsEntity.setAnswer(option.getAnswer());
                            optionsEntity.setSuggestion(option.getSuggestion());
                            return optionsEntity;
                        })
                        .collect(Collectors.toList());
                optionsrepository.saveAll(optionsEntities);
                question.setOptions(optionsEntities);
            }
        }
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
}



