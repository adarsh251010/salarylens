package com.adarsh.salarylens.controller;

import com.adarsh.salarylens.dto.SalaryRequestDTO;
import com.adarsh.salarylens.dto.SalaryResponseDTO;
import com.adarsh.salarylens.service.SalaryAnalysisService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<SalaryResponseDTO>> getAllSalaries() {
        return ResponseEntity.ok(service.getAllSalaries());
    }

    @PostMapping
    public ResponseEntity<SalaryResponseDTO> saveSalary(@Valid @RequestBody SalaryRequestDTO requestDTO) {
        return ResponseEntity.ok(service.saveSalary(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaryResponseDTO> getSalaryById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSalaryById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalary(@PathVariable Long id) {
        service.deleteSalary(id);
        return ResponseEntity.ok("Salary record deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaryResponseDTO> updateSalary(
            @PathVariable Long id,
            @Valid @RequestBody SalaryRequestDTO requestDTO) {
        return ResponseEntity.ok(service.updateSalary(id, requestDTO));
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<SalaryResponseDTO>> getAllSalariesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllSalariesPaginated(page, size));
    }

    @GetMapping("/location")
    public ResponseEntity<List<SalaryResponseDTO>> getSalariesByLocation(
            @RequestParam String location) {
        return ResponseEntity.ok(service.getSalariesByLocation(location));
    }

    @GetMapping("/experience")
    public ResponseEntity<List<SalaryResponseDTO>> getSalariesByExperience(
            @RequestParam Integer years) {
        return ResponseEntity.ok(service.getSalariesByExperience(years));
    }
}