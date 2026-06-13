package com.adarsh.salarylens.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class SalaryRequestDTO implements Serializable {

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    private Integer experienceYears;

    @NotNull(message = "Salary offered is required")
    private Double salaryOffered;

    @NotNull(message = "Last salary is required")
    private Double lastSalary;

    @NotBlank(message = "Location is required")
    private String location;
}