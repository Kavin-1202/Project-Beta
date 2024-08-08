package com.ust.AssessmentApi.repository;

import com.ust.AssessmentApi.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface Setrepository extends JpaRepository<Set,Long> {

}
