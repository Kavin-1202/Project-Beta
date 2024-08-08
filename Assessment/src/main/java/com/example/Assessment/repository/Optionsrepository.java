package com.example.Assessment.repository;


import com.example.Assessment.model.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Optionsrepository extends JpaRepository<Options,Long> {

}
