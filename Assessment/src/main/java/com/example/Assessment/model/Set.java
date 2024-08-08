package com.example.Assessment.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="assessments")
public class Set {
    @Id
    private Long setId;
    private String setName;
    private String createdby;
    private String domain;
    @Enumerated(value= EnumType.STRING)
    private Status status;
    private String updated_by;
    private Date createdTimestamp;
    private Date updatedTimestamp;

    @OneToMany(cascade= CascadeType.ALL)
    //@JoinColumn(name="setId")
    private List<Questions> questionList;
}
