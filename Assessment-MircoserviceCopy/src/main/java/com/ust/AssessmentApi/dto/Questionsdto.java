package com.ust.AssessmentApi.dto;

import com.ust.AssessmentApi.model.Options;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questionsdto {
    private String question_description;
    private List<Optionsdto> optionsdtoList;
}
