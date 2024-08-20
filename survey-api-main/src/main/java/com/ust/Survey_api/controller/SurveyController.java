package com.ust.Survey_api.controller;

import com.ust.Survey_api.exception.SetNotFoundException;
import com.ust.Survey_api.feign.FullResponse;
import com.ust.Survey_api.feign.SetNameDto;
import com.ust.Survey_api.model.Survey;
import com.ust.Survey_api.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @PostMapping("/survey")
    public ResponseEntity<FullResponse> addSurvey(@RequestBody Survey survey) {
       FullResponse surveyResponse = surveyService.addSurvey(survey);
        if (surveyResponse == null) {
            throw new SetNotFoundException("Set name not found.");
        }
        return ResponseEntity.ok(surveyResponse);
    }

    @GetMapping("/surveys")
    public ResponseEntity<List<FullResponse>> getSurveys() {
        return ResponseEntity.ok(surveyService.getSurveys());
    }

    @GetMapping("/survey/surveyId/{surveyId}")
    public ResponseEntity<?> getSurveyById(@PathVariable Long surveyId) {
        FullResponse surveyOptional = surveyService.getSurveyById(surveyId);
        if(surveyOptional == null){
            throw new SetNotFoundException("Invalid surveyId");
        }
        return ResponseEntity.ok(surveyService.getSurveyById(surveyId));
    }

    @GetMapping("/survey/setName/{setName}")
    public ResponseEntity<?> getQuestionsBySetName(@PathVariable String setName){
        List<SetNameDto> listOfQuestions= surveyService.getSet(setName);
        return ResponseEntity.ok(listOfQuestions);
    }



    @ExceptionHandler(SetNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handleNotFoundException(SetNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }
}
