package com.ust.AssessmentApi.service;

import com.ust.AssessmentApi.dto.Questionsdto;
import com.ust.AssessmentApi.dto.Setdto;
import com.ust.AssessmentApi.model.Person;
import com.ust.AssessmentApi.model.Questions;
import com.ust.AssessmentApi.model.Set;
import com.ust.AssessmentApi.repository.Optionsrepository;
import com.ust.AssessmentApi.repository.Questionsrepository;
import com.ust.AssessmentApi.repository.Setrepository;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class Assessmentservice {

    @Autowired
    private Optionsrepository optionsrepository;

    @Autowired
    private Questionsrepository questionsRepository;

    @Autowired
    private Setrepository setrepository;


    public List<Set> getAllSets(){
        return setrepository.findAll();
    }

    public Setdto createSet(Setdto dto1) {
        Setdto setdto=new Setdto();
        setdto.setSetname(dto1.getSetname());
        setdto.setDomain(dto1.getDomain());
        setdto.setCreatedby(Person.getName());
        setdto.setLocalDateTime(LocalDateTime.now());
        setdto.setQuestionsList( dto1.getQuestionsList());
        return setrepository.save(setdto);
    }


    public Set getSet(String setname) {
        return setrepository.findBySetname(setname);
    }
    public Questions updateQuestion(String setname, Long questionId, Questionsdto qdto) {
        Questions questions = questionsRepository.findBySetname(setname);
        if (questions.getQuestionid().equals(questionId)) {
            questions.setOptions(qdto.getOptions());
            BeanUtils.copyProperties(qdto, questions);
            questionsRepository.save(questions);
        }
        return questions;
    }

    public void deleteQuestion(String setname, Long questionId) {
        Questions questions = questionsRepository.findBySetname(setname);
//        questionsRepository.deletebyId(questions.getQuestionid());
        questionsRepository.deleteById(questionId);
    }


//        question.setOptions(qdto.getOptions());
//        return questionsRepository.save(question);
    }

//    public Set updateQuestion(String setname, Long questionId, Questionsdto qdto) {
//        Questions question= questionsRepository.findBySetname(setname);
//        question.setOptions(qdto.getOptions());
//        return questionsRepository.save(question);
//    }


