package com.example.Assessment.service;

import com.example.Assessment.Exception.SetNotFoundException;
import com.example.Assessment.dto.Optionsdto;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ServiceImplementation implements AssessmentService {


    private static final AtomicLong setcounter = new AtomicLong(0);
    private static final AtomicLong questioncounter = new AtomicLong(0);
    private static final AtomicLong optioncounter = new AtomicLong(0);

    @Autowired
    private Optionsrepository optionsrepository;

    @Autowired
    private Questionsrepository questionsRepository;

    @Autowired
    private Setrepository setrepository;


    public List<Set> getAllSets() {
        return setrepository.findAll();
    }

//    public Set createSet(Setdto set1) {
//        Set set = new Set();
//        long setid = generateUniqueNumber();
//        set.setSetId(setid);
//        set.setSetName(set1.getSetName());
//        set.setDomain(set1.getDomain());
//        set.setCreatedby(Person.getName());
//        set.setUpdated_by(Person.getName());
//        set.setStatus(Status.PENDING);
//        set.setCreatedTimestamp(new Date());
//        set.setUpdatedTimestamp(new Date());
//
//        List<Questions> questionEntities = set1.getQuestionList().stream()
//                .map(questions -> {
//                    Questions questionEntity = new Questions();
//                    long question_id=generateUniqueNumberForQuestionId();
//                    questionEntity.setSetId(setid);
//                    questionEntity.setQuestion_id(question_id);
//                    questionEntity.setQuestion_description(questions.getQuestion_description());
//
//                    // Map options if provided by the user
//                    List<Options> optionsEntities = questions.getOptionsdtoList() != null
//                            ? questions.getOptionsdtoList().stream()
//                            .map(option -> {
//                                Options optionEntity = new Options();
//                                //optionEntity.setOptionid(generateUniqueNumberForOptionId()); // Generate a unique option id
//                                optionEntity.setQuestion_id(questionEntity.getQuestion_id()); // Link the option to the question
//                                optionEntity.setAnswer(option.getAnswer());
//                                optionEntity.setSuggestion(option.getSuggestion());
//                                return optionEntity;
//                            })
//                            .collect(Collectors.toList())
//                            : Collections.emptyList();
//
//                    questionEntity.setOptions(optionsEntities); // Set options list to the question entity
//                    return questionEntity;
//                })
//                .collect(Collectors.toList());
//
//        questionsRepository.saveAll(questionEntities);
//
//        set.setQuestionList(questionEntities);
//        setrepository.save(set);
//        return set;
//    }

    public List<SetNameDto> getSet(String  setName) {
        Set set = setrepository.findBySetNameIgnoreCase(setName).orElse(null);
        if(set == null) {
            throw new SetNotFoundException("Set not found.");
        }
        List<Questions> questions= set.getQuestionList();

        return questions.stream().map(question -> {
            SetNameDto setNameDto = new SetNameDto();
            setNameDto.setQuestion_id(question.getQuestion_id());
            setNameDto.setQuestion_description(question.getQuestion_description());
            return setNameDto;
        }).collect(Collectors.toList());
    }
    public Set createSet(Setdto setDto) {
        Set set = new Set();
        set.setSetId(generateUniqueNumber());
        set.setSetName(setDto.getSetName());
        set.setDomain(setDto.getDomain());
        set.setCreatedby(Person.getName());
        set.setUpdated_by(Person.getName());
        set.setStatus(Status.PENDING);
        set.setCreatedTimestamp(new Date());
        set.setUpdatedTimestamp(new Date());

        List<Questions> questionEntities = setDto.getQuestionList().stream()
                .map(questionDto -> {
                    Questions questionEntity = new Questions();
                    questionEntity.setSetId(set.getSetId());
                    questionEntity.setQuestion_id(generateUniqueNumberForQuestionId());
                    questionEntity.setQuestion_description(questionDto.getQuestion_description());

                    List<Options> optionsEntities = (questionDto.getOptionsdtoList() != null && !questionDto.getOptionsdtoList().isEmpty())
                            ? questionDto.getOptionsdtoList().stream()
                            .map(optionDto -> {
                                Options optionEntity = new Options();
                                optionEntity.setQuestion_id(questionEntity.getQuestion_id());// Generate a unique option id
                                optionEntity.setAnswer(optionDto.getAnswer());
                                optionEntity.setSuggestion(optionDto.getSuggestion());
                                return optionEntity;
                            })
                            .collect(Collectors.toList())
                            : null;

                    questionEntity.setOptions(optionsEntities);
                    return questionEntity;
                })
                .collect(Collectors.toList());

        set.setQuestionList(questionEntities);
        return setrepository.save(set);
    }

    public Questions updateQuestion(Long setId, Long questionId, Questionsdto questionDto) {
        Set set = setrepository.findById(setId).orElse(null);
        if (set == null) {
            throw new SetNotFoundException("Set not found.");
        }

        Questions question = questionsRepository.findById(questionId).orElse(null);
        if (question == null || !question.getSetId().equals(setId)) {
            throw new SetNotFoundException("Question not found.");
        }

        List<Options> existingOptions = question.getOptions();
        if (existingOptions == null) {
            existingOptions = new ArrayList<>();
        }

        List<Options> newOptionsEntities = questionDto.getOptionsdtoList().stream()
                .map(optionDto -> {
                    Options optionsEntity = new Options();
//                    optionsEntity.setOptionid(generateUniqueNumberForOptionId()); // Generate a unique option id
                    optionsEntity.setQuestion_id(questionId);// Link the option to the question
                    optionsEntity.setAnswer(optionDto.getAnswer());
                    optionsEntity.setSuggestion(optionDto.getSuggestion());
                    return optionsEntity;
                })
                .collect(Collectors.toList());

        existingOptions.addAll(newOptionsEntities);
        optionsrepository.saveAll(newOptionsEntities);
        question.setOptions(existingOptions);
        Questions q=questionsRepository.save(question);
        return questionsRepository.save(q);
    }

//    public List<Questions> updateQuestion(Long setId, Long question_id, Questionsdto qdto) {
//        // Retrieve the set by ID
//        Set set = setrepository.findById(setId).orElse(null);
//        if (set == null) {
//            throw new SetNotFoundException("Set not found.");
//        }
//
//        // Update the set's timestamp and updated_by fields
//        set.setUpdatedTimestamp(new Date());
//        set.setUpdated_by(Person.getName());
//
//        // Retrieve the question to be updated
//        Questions question = questionsRepository.findById(question_id).orElse(null);
//        if (question == null || !question.getSetId().equals(setId)) {
//            throw new SetNotFoundException("Question not found.");
//        }
//
//        // Retrieve existing options
//        List<Options> existingOptions = question.getOptions();
//        if (existingOptions == null) {
//            existingOptions = new ArrayList<>();
//        }
//
////        // Generate unique ID starting from the current maximum option ID for the question
//        final Long[] maxOptionId = {existingOptions.stream()
//                .mapToLong(Options::getOptionid)
//                .max()
//                .orElse(0L)};
//
//        List<Options> newOptionsEntities = qdto.getOptionsdtoList().stream()
//                .map(option -> {
//                    Options optionsEntity = new Options();
//                    optionsEntity.setOptionid(++maxOptionId[0]); // Generate unique option ID
//                    optionsEntity.setQuestion_id(question_id);
//                    optionsEntity.setAnswer(option.getAnswer());
//                    optionsEntity.setSuggestion(option.getSuggestion());
//                    return optionsEntity;
//                })
//                .collect(Collectors.toList());
//
//        // Add new options to existing options list
//        existingOptions.addAll(newOptionsEntities);
//
//        // Detach any existing instances to avoid conflict
//        optionsrepository.saveAll(newOptionsEntities);
//
//        // Update the question's options with the merged list
//        question.setOptions(existingOptions);
//
//        // Save the updated question to the database
//        questionsRepository.save(question);
//        set.setQuestionList(List.of(question));
//        setrepository.save(set);
//        return List.of(question);
//    }

    public boolean deleteQuestion(Long setId, Long question_id) {
        Set set = setrepository.findById(setId).orElse(null);
        if(set == null) {
            throw new SetNotFoundException("Set not found.");
        }
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

        Set set= setrepository.findById(setId).orElse(null);
        if(set == null) {
            throw new SetNotFoundException("Set not found.");
        }
        return set;
    }

    public static long generateUniqueNumber() {
        return setcounter.incrementAndGet();
    }
    public static long generateUniqueNumberForQuestionId() {
        return questioncounter.incrementAndGet();
    }
    public static long generateUniqueNumberForOptionId() {
        return optioncounter.incrementAndGet();
    }
}


