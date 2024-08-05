package com.ust.AssessmentApi.repository;

import com.ust.AssessmentApi.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Setrepository extends JpaRepository<Set,Long> {
    Set findBySetname(String setname);
}
