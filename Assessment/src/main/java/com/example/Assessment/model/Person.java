package com.example.Assessment.model;


import jakarta.persistence.Entity;
import lombok.Data;


public class Person {
    static String name= "Suresh";

    public static String getName() {
        return name;
    }
}
