package com.ust.AssessmentApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionid;
    private String setname;
    private String questiontext;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Options> options;

}
