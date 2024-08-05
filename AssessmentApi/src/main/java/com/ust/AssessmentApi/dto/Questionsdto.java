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
    private Long questionid;
    private String questiontext;
    private List<Options> options;
}
