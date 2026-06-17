package com.adarsh.salarylens.controller;

import com.adarsh.salarylens.dto.SalaryRequestDTO;
import com.adarsh.salarylens.dto.SalaryResponseDTO;
import com.adarsh.salarylens.service.SalaryAnalysisService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private final SalaryAnalysisService service;

    public SalaryController(SalaryAnalysisService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SalaryResponseDTO>> getAllSalaries(
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(service.getAllSalaries(username));
    }

    @PostMapping
    public ResponseEntity<SalaryResponseDTO> saveSalary(
            @Valid @RequestBody SalaryRequestDTO requestDTO,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(service.saveSalary(requestDTO, username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaryResponseDTO> getSalaryById(
            @PathVariable Long id,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(service.getSalaryById(id, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalary(
            @PathVariable Long id,
            @AuthenticationPrincipal String username) {
        service.deleteSalary(id, username);
        return ResponseEntity.ok("Salary record deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaryResponseDTO> updateSalary(
            @PathVariable Long id,
            @Valid @RequestBody SalaryRequestDTO requestDTO,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(service.updateSalary(id, requestDTO, username));
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<SalaryResponseDTO>> getAllSalariesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(service.getAllSalariesPaginated(page, size, username));
    }

    @GetMapping("/location")
    public ResponseEntity<List<SalaryResponseDTO>> getSalariesByLocation(
            @RequestParam String location,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(service.getSalariesByLocation(location, username));
    }

    @GetMapping("/experience")
    public ResponseEntity<List<SalaryResponseDTO>> getSalariesByExperience(
            @RequestParam Integer years,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(service.getSalariesByExperience(years, username));
    }
}