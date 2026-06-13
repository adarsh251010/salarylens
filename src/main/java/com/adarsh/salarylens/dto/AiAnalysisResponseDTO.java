package com.adarsh.salarylens.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiAnalysisResponseDTO implements Serializable {

    private String analysis;
    private String negotiationScript;
    private String suggestedSalary;
}