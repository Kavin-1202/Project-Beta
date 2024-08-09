package com.example.Assessment.controller;


import com.example.Assessment.Exception.SetNotFoundException;
import com.example.Assessment.dto.Questionsdto;
import com.example.Assessment.dto.SetNameDto;
import com.example.Assessment.dto.Setdto;
import com.example.Assessment.model.Questions;
import com.example.Assessment.model.Set;
import com.example.Assessment.service.ServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController

public class Assessmentcontroller {

    @Autowired
    private ServiceImplementation assessmentService;

    @GetMapping("/assessments")
    public ResponseEntity<List<Set>> getAllSets(){
        return ResponseEntity.ok(assessmentService.getAllSets());
    }

    @PostMapping("/assessment")
    public ResponseEntity<Set> createquestion(@RequestBody Setdto set1){
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentService.createSet(set1));
    }

    @GetMapping("/assessment/{setName}")
    public ResponseEntity<List<SetNameDto>> getSet(@PathVariable String setName){
        List<SetNameDto> set = assessmentService.getSet(setName);
        if (set == null) {
            throw new SetNotFoundException("Set not found.");
        }
        return ResponseEntity.ok(set);
    }

    @ExceptionHandler(SetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleSetNotFoundException(SetNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PutMapping("/assessment/{setId}/{question_id}")
    public ResponseEntity<List<Questions>>updateQuestion(@PathVariable Long setId, @PathVariable Long question_id, @RequestBody Questionsdto qdto){
        Set set = assessmentService.getSetById(setId);
        if (set == null) {
            throw new SetNotFoundException("Set not found.");
        }
        return ResponseEntity.ok(assessmentService.updateQuestion(setId, question_id, qdto));
    }


    @DeleteMapping("/assessment/{setId}/{question_id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long setId, @PathVariable Long question_id) {
        Boolean del = assessmentService.deleteQuestion(setId, question_id);
        if (del) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Failed to delete, question not found.");
        }
    }

}
