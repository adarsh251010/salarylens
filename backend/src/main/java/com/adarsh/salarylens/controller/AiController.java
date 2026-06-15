package com.adarsh.salarylens.controller;

import com.adarsh.salarylens.dto.SalaryRequestDTO;
import com.adarsh.salarylens.service.AsyncJobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AsyncJobService asyncJobService;

    public AiController(AsyncJobService asyncJobService) {
        this.asyncJobService = asyncJobService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<Map<String, String>> analyzeSalary(
            @Valid @RequestBody SalaryRequestDTO requestDTO,
            @AuthenticationPrincipal String username) {
        String jobId = asyncJobService.submitJob(requestDTO, username);
        return ResponseEntity.ok(Map.of(
                "jobId", jobId,
                "status", "PROCESSING",
                "message", "Job submitted! Use jobId to check result."
        ));
    }

    @GetMapping("/result/{jobId}")
    public ResponseEntity<Object> getResult(
            @PathVariable String jobId,
            @AuthenticationPrincipal String username) {
        Object result = asyncJobService.getJobResult(jobId, username);
        return ResponseEntity.ok(result);
    }
}