package com.adarsh.salarylens.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "salary_data")
public class SalaryData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private Integer experienceYears;
    private Double salaryOffered;
    private Double lastSalary;
    private String location;
}
