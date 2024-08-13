package com.ust.Survey_api.service;

import com.ust.Survey_api.feign.AssessmentClient;
import com.ust.Survey_api.feign.FullResponse;
import com.ust.Survey_api.model.Survey;
import com.ust.Survey_api.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ServiceImpl  implements  SurveyService{

    private static final AtomicLong counter = new AtomicLong(0);

    @Autowired
    private AssessmentClient client;

    @Autowired
    private SurveyRepository repo;

    @Override
    public FullResponse addSurvey(Survey survey) {
        FullResponse fr = new FullResponse();
        long id = counter.incrementAndGet();
        fr.setSurveyId(id);
        fr.setDomain(survey.getDomain());
        fr.setStatus(survey.getStatus());
        fr.setEmail(survey.getEmail());
        fr.setSetName(survey.getSetName());
        fr.setCompanyName(survey.getCompanyName());
        fr.setSetdata(client.getSet(survey.getSetName()).getBody());
        repo.save(survey);
        return fr;
    }

    @Override
    public List<FullResponse> getSurveys() {
        List<FullResponse> frs = new ArrayList<FullResponse>();
        List<Survey> surveys =  repo.findAll();
        for (Survey survey : surveys) {
            FullResponse fr = new FullResponse();
            long id = counter.incrementAndGet();
            fr.setSurveyId(survey.getSurveyId());
            fr.setDomain(survey.getDomain());
            fr.setStatus(survey.getStatus());
            fr.setEmail(survey.getEmail());
            fr.setSetName(survey.getSetName());
            fr.setCompanyName(survey.getCompanyName());
            fr.setSetdata(client.getSet(survey.getSetName()).getBody());
            frs.add(fr);
        }
        return frs;
    }




}
