package com.ust.Survey_api.service;

import com.ust.Survey_api.exception.SetNotFoundException;
import com.ust.Survey_api.feign.AssessmentClient;
import com.ust.Survey_api.feign.FullResponse;
import com.ust.Survey_api.feign.SetNameDto;
import com.ust.Survey_api.model.Survey;
import com.ust.Survey_api.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
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
        List<SetNameDto> optionalSetData = null;
        try {
            optionalSetData = client.getSet(survey.getSetName()).getBody();
        } catch (Exception e) {
            throw new SetNotFoundException("Set not found.");
        }
        List<SetNameDto> ques=new ArrayList<>();
        for(SetNameDto setName : optionalSetData){
            if(StringToList(survey.getQuestionId()).contains(setName.getQuestion_id())){
                ques.add(setName);
            }
        }
        if(ques.isEmpty()){
            throw new SetNotFoundException("No valid questions found in set");
        }
        fr.setSetdata(ques);
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
            List<SetNameDto> dtos = client.getSet(survey.getSetName()).getBody();
            String questionids=survey.getQuestionId();
            List<SetNameDto> dtoList = getQuestionsbyid(dtos,questionids);
            fr.setSetdata(dtoList);
            frs.add(fr);
        }
        return frs;
    }
    public List<SetNameDto> getQuestionsbyid(List<SetNameDto> dto,String questionid){
        List<SetNameDto> dtoList = new ArrayList<SetNameDto>();
        for(SetNameDto ques : dto){
            if(StringToList(questionid).contains(ques.getQuestion_id())){
                dtoList.add(ques);
            }
        }
        if(dtoList.isEmpty()){
            throw new SetNotFoundException("question not found in set");
        }
        return dtoList;
    }

    @Override
    public FullResponse getSurveyById(Long surveyId) {
        FullResponse fr = new FullResponse();
        Survey survey = repo.findBySurveyId(surveyId);
        if(survey == null) {
            throw new SetNotFoundException("Invalid survey id");
        }
        fr.setSurveyId(survey.getSurveyId());
        fr.setDomain(survey.getDomain());
        fr.setStatus(survey.getStatus());
        fr.setEmail(survey.getEmail());
        fr.setSetName(survey.getSetName());
        fr.setCompanyName(survey.getCompanyName());
        List<SetNameDto> dtos = client.getSet(survey.getSetName()).getBody();
        String questionids=survey.getQuestionId();
        List<SetNameDto> dtoList = getQuestionsbyid(dtos,questionids);
        fr.setSetdata(dtoList);
        return fr;
    }

    @Override
    public List<SetNameDto> getSet(String setName) {
        List<SetNameDto> setdata= null;
        try{
            setdata =client.getSet(setName).getBody();
            return setdata;
        }
        catch(Exception e){
            throw new SetNotFoundException("Invalid setname");
        }
    }
    public List<Long>StringToList(String questionids){
        List<Long> list = Arrays.stream(questionids.split(",")).map(Long::parseLong).toList();
        return list;
    }


}
