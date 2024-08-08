package com.example.Assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questionsdto {
    public String question_description;
    private List<Optionsdto> optionsdtoList;
}
