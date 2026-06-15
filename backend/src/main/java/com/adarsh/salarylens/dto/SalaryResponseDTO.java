package com.adarsh.salarylens.dto;

import lombok.Data;

@Data
public class SalaryResponseDTO {

    private Long id;
    private String jobTitle;
    private Integer experienceYears;
    private Double salaryOffered;
    private Double lastSalary;
    private String location;
}