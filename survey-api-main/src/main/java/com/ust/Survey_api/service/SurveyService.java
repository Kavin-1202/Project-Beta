package com.ust.Survey_api.service;

import com.ust.Survey_api.feign.FullResponse;
import com.ust.Survey_api.feign.SetNameDto;
import com.ust.Survey_api.model.Survey;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SurveyService {

    FullResponse addSurvey(Survey survey);

    List<FullResponse> getSurveys();

    FullResponse getSurveyById(Long surveyId);

    List<SetNameDto> getSet(String setName);
}
