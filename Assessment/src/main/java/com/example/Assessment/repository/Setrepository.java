package com.example.Assessment.repository;


import com.example.Assessment.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface Setrepository extends JpaRepository<Set,Long> {

    Optional<Set> findBySetNameIgnoreCase(String setName);
}
