package com.example.Assessment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setdto {
    private String setName;
    private String domain;
    private List<Questionsdto> questionList;
}
