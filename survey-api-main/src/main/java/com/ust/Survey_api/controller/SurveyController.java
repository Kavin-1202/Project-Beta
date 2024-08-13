package com.ust.Survey_api.controller;

import com.ust.Survey_api.feign.FullResponse;
import com.ust.Survey_api.feign.SetNameDto;
import com.ust.Survey_api.model.Survey;
import com.ust.Survey_api.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @PostMapping("/survey")
    public FullResponse addSurvey(@RequestBody Survey survey) {
        return surveyService.addSurvey(survey);
    }

    @GetMapping("/surveys")
    public List<FullResponse> getSurveys() {
        return surveyService.getSurveys();
    }


}
