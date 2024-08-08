package com.example.Assessment.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetNameDto {
    public Long question_id;
    public String question_description;
}
