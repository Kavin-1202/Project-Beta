package com.ust.AssessmentApi.dto;

import com.ust.AssessmentApi.model.Questions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setdto {

    private Long setId;
    private String set_description;
    private String domain;
    private List<Questions> questionList;
}
