package com.ust.AssessmentApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="options")
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionid;
    private Long questionid;
    private String value;
    private String suggestions;
}
