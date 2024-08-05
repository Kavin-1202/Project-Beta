package com.ust.AssessmentApi.controller;


import com.ust.AssessmentApi.dto.Questionsdto;
import com.ust.AssessmentApi.dto.Setdto;
import com.ust.AssessmentApi.model.Questions;
import com.ust.AssessmentApi.model.Set;
import com.ust.AssessmentApi.service.Assessmentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class Assessmentcontroller {

    @Autowired
    private Assessmentservice assessmentService;

    @GetMapping("/assessments")
    public List<Set> getAllSets(){
        return assessmentService.getAllSets();
    }

    @PostMapping("/assessment/setname")
    public Setdto createquestion(@RequestBody Setdto dto1){
        return assessmentService.createSet(dto1);

    }

    @GetMapping("/assessment/{setname}")
    public Set getSet(@PathVariable String setname){
        return assessmentService.getSet(setname);
    }

    @PutMapping("/assessment/{setname}/edit/{questionId}")
    public Questions updateQuestion(@PathVariable String setname, @PathVariable Long questionId, @RequestBody Questionsdto qdto){
        return assessmentService.updateQuestion(setname, questionId, qdto);
    }
    @DeleteMapping("/assessment/{setname}/delete/{questionId}")
    public void deleteQuestion(@PathVariable String setname, @PathVariable Long questionId){
        assessmentService.deleteQuestion(setname, questionId);
    }

}
