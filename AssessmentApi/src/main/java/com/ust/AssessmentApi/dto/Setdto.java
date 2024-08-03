package com.ust.AssessmentApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setdto {

    private String setname;
    private String domain;
    private String createdby;
    private LocalDateTime localDateTime;
    private List<Questionsdto> questionsList;
}
