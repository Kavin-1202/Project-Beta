package com.ust.AssessmentApi.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;
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
    private String set_description;
    private String createdby;
    private String domain;

    @Enumerated(value= EnumType.STRING)
    private Status status;

    private String updated_by;

    private Date createdTimestamp;
    private Date updatedTimestamp;



    @OneToMany(cascade= CascadeType.ALL)
    private List<Questions> questionList;
}
