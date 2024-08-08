package com.ust.AssessmentApi.utils;

import com.ust.AssessmentApi.dto.Questionsdto;
import com.ust.AssessmentApi.dto.Setdto;
import com.ust.AssessmentApi.model.Questions;
import com.ust.AssessmentApi.model.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

public class Apputils {

    @Bean
    public Setdto dtoToEntity(Set set){
        Setdto setdto1 = new Setdto();
        BeanUtils.copyProperties(set,setdto1);
        return setdto1;
    }

    @Bean
    public Set entityToDto(Setdto setdto){
        Set set1 = new Set();
        BeanUtils.copyProperties(setdto,set1);
        return set1;
    }

    @Bean
    public Questionsdto dtoToEntity(Questions questions){
        Questionsdto questionsdto1 = new Questionsdto();
        BeanUtils.copyProperties(questions,questionsdto1);
        return questionsdto1;
    }

    @Bean
    public Questions EntityToDto(Questionsdto questionsdto){
        Questions questions1 = new Questions();
        BeanUtils.copyProperties(questionsdto,questions1);
        return questions1;
    }

}
