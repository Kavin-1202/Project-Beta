package com.ust.AssessmentApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="assessments")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sino;
    private String setname;
    private String createdby;
    private String domain;
    @Enumerated(value= EnumType.STRING)
    private status status;
}
